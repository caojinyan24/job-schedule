package swa.job.schedule;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Service;
import swa.db.entity.JobInfo;
import swa.db.service.JobManagerService;
import swa.db.service.JobScheduleService;
import swa.job.common.JobContext;
import swa.job.common.MsgBody;
import swa.rpc.Client;
import swa.util.ScheduleStatusEnum;

import javax.annotation.Resource;

/**
 * 任务调度的核心类
 * Created by jinyan on 10/20/17 3:42 PM.
 */
@Service
public class JobExecutor {
    private static final Logger logger = LoggerFactory.getLogger(JobExecutor.class);

    @Resource
    private JobManagerService jobManagerService;
    @Resource
    private JobScheduleService jobScheduleService;

    /**
     * 立即执行/修改任务调度时间/修改任务执行机器后，向client发送job信息
     *
     * @param jobId
     */
    public void sendJob(final Long jobId) {
        final JobContext jobContext = jobManagerService.getExecuteJobInfo(jobId);
        sendJob(jobContext);
    }

    public void sendJob(final JobContext jobContext) {
        if (null == jobContext) {
            throw new SchedulingException("job not exist");
        }
        try {
            new Client(jobContext.getAddress(), jobContext.getPort(), JSON.toJSONString(jobContext)).start();
            jobScheduleService.addJobScheduleHistory(jobContext, ScheduleStatusEnum.SUCCESS);
        } catch (InterruptedException e) {
            logger.error("sendJob-end", e);
            jobScheduleService.addJobScheduleHistory(jobContext, ScheduleStatusEnum.FAIL);
        }
    }

    public void sendJob(String ip, Integer port, final String msg) {
        if (Strings.isNullOrEmpty(msg)) {
            throw new SchedulingException("job not exist");
        }
        try {
            new Client(ip, port, msg).start();
        } catch (InterruptedException e) {
            logger.error("sendJob-end", e);
        }
    }


    public void sendAddJob(final JobContext jobContext) {
        MsgBody msgBody = new MsgBody();
        JobContext newJob = new JobContext();
        BeanUtils.copyProperties(jobContext, newJob);
        msgBody.setCode("ADD");
        msgBody.setNewJob(newJob);
        sendJob(jobContext.getAddress(), jobContext.getPort(), JSON.toJSONString(msgBody));

    }

    public void sendModifiedJob(final JobContext jobContext,final JobInfo jobInfo) {
        MsgBody msgBody = new MsgBody();
        msgBody.setCode("MODIFY");
        msgBody.setOldJob(jobContext);

        JobContext newJob=new JobContext(jobInfo,jobContext.getAddress(),jobContext.getPort());
        msgBody.setNewJob(newJob);
        sendJob(jobContext.getAddress(), jobContext.getPort(), JSON.toJSONString(msgBody));

    }


}


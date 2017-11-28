package swa.job.schedule;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Service;
import swa.db.service.JobManagerService;
import swa.db.service.JobScheduleService;
import swa.job.common.JobContext;
import swa.rpc.Client;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    /**
     * 立即执行/修改任务调度时间/修改任务执行机器后，向client发送job信息
     *
     * @param jobId
     */
    public void sendJob(final Long jobId) {
        logger.info("schedule job:{}", jobId);
        final JobContext jobContext = jobManagerService.getExecuteJobInfo(jobId);
        if (null == jobContext) {
            throw new SchedulingException("job not exist");
        }
        logger.debug("execute job:{}", jobContext);
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    // TODO: 11/28/17 验证执行时间 
                    new Client(jobContext.getAddress(), jobContext.getPort(), JSON.toJSONString(jobContext)).start();
                } catch (InterruptedException e) {
                    throw new SchedulingException("schedule error");
                }
            }
        });
        jobScheduleService.addJobScheduleHistory(jobContext);

    }


}

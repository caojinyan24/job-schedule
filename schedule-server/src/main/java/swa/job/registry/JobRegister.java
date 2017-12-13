package swa.job.registry;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import swa.db.entity.App;
import swa.db.entity.JobInfo;
import swa.db.mapper.AppMapper;
import swa.db.mapper.JobInfoMapper;
import swa.db.service.JobManagerService;
import swa.exception.JobScheduleException;
import swa.exception.PreconditionUtil;
import swa.job.common.JobContext;
import swa.job.common.MsgBody;
import swa.job.schedule.JobExecutor;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jinyan on 11/3/17 5:55 PM.
 */
@Service
public class JobRegister {
    private static final Logger logger = LoggerFactory.getLogger(JobRegister.class);

    @Resource
    private JobInfoMapper jobInfoMapper;
    @Resource
    private AppMapper appMapper;
    @Resource
    private JobManagerService jobManagerService;
    @Resource
    private JobExecutor scheduleExecutor;

    /**
     * 组装并保存任务信息到数据库
     * 取hashcode做appcode
     *
     * @param jobContext
     */
    private void saveJobInfo(JobContext jobContext) {

        PreconditionUtil.check(null != jobContext, "innvalid jobInfo");
        PreconditionUtil.check(!Strings.isNullOrEmpty(jobContext.getAppName()), "appName is empty");
        PreconditionUtil.check(!Strings.isNullOrEmpty(jobContext.getBeanName()), "beanName is empty");
        PreconditionUtil.check(!Strings.isNullOrEmpty(jobContext.getMethodName()), "methodName is empty");
        PreconditionUtil.check(null != jobContext.getPort(), "port is empty");
        if (!jobManagerService.isExist(jobContext.getAppName(), jobContext.getBeanName(), jobContext.getMethodName())) {
            JobInfo jobInfo = new JobInfo(jobContext.getAppName(), jobContext.getBeanName(), jobContext.getMethodName());
            try {
                jobInfoMapper.insertJob(jobInfo);
            } catch (DuplicateKeyException e) {
                logger.info("same job exists");
            }
        }
        App applicationInfo = appMapper.selectByAppName(jobContext.getAppName());
        if (applicationInfo == null) {
            applicationInfo = new App();
            applicationInfo.setAppName(jobContext.getAppName());
        }
        if (!jobContext.getPort().equals(applicationInfo.getPort())) {
            applicationInfo.setAddress("127.0.0.1");//todo 后边改成从页面修改
            applicationInfo.setPort(jobContext.getPort());
            appMapper.insertOrUpdate(applicationInfo);
        }
    }

    public void registerJob(String jobInfoStr) {
        logger.info("registerJob:{}", jobInfoStr);
        MsgBody msgBody = JSON.parseObject(jobInfoStr, MsgBody.class);
        JobInfo jobInfo = new JobInfo();
        jobInfo.setMethodName(msgBody.getNewJob().getMethodName());
        jobInfo.setBeanName(msgBody.getNewJob().getBeanName());
        jobInfo.setAppName(msgBody.getNewJob().getAppName());
        jobInfo.setId(msgBody.getNewJob().getJobId());
        jobInfo.setCronParam(msgBody.getNewJob().getCronParam());
        jobInfo.setParam(msgBody.getNewJob().getParam());
        jobInfo.setScheduleAddr(msgBody.getNewJob().getAddress());
        saveJobInfo(msgBody.getNewJob());
        List<JobInfo> dbData = jobInfoMapper.selectSelective(jobInfo);
        logger.info("register:{}", dbData);
        if (dbData.size() != 1) {
            throw new JobScheduleException("data error");
        }
        JobInfo currentJob = dbData.get(0);
        JobContext addJobContext = msgBody.getNewJob();
        addJobContext.setAddress(currentJob.getScheduleAddr());
        addJobContext.setCronParam(currentJob.getCronParam());
        addJobContext.setJobId(currentJob.getId());
        logger.debug("registerJob:{}", addJobContext);
        if (!Strings.isNullOrEmpty(currentJob.getScheduleAddr())) {//对注册任务，要求必须配置执行时间
            scheduleExecutor.sendAddJob(addJobContext);
        }
    }

}

package swa.job.cronParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronSequenceGenerator;
import swa.job.JobInfo;

import java.util.Date;

/**
 * Created by jinyan on 11/2/17 3:07 PM.
 */
public class JobInfoWrapper extends JobInfo {
    private static final Logger logger = LoggerFactory.getLogger(JobInfoWrapper.class);

    Date nextExecuteTime;
    Date currentExecuteTime;
    Date actualLastExecuteTime;

    /**
     * 加入新任务
     *
     * @param jobInfo
     */
    public static JobInfoWrapper newWrapperJob(JobInfo jobInfo) {
        return new JobInfoWrapper(jobInfo);

    }

    public static Boolean isCronParamValid(JobInfo jobInfo) {
        return jobInfo.getCronParam() != null && jobInfo.getCronParam().length() != 0;
    }

    public JobInfoWrapper() {
    }

    private JobInfoWrapper(JobInfo jobInfo) {
        setJobId(jobInfo.getJobId());
        setAppName(jobInfo.getAppName());
        setBeanName(jobInfo.getBeanName());
        setMethodName(jobInfo.getMethodName());
        setPort(jobInfo.getPort());
        setAddress(jobInfo.getAddress());
        setCronParam(jobInfo.getCronParam());
        setParam(jobInfo.getParam());
        this.actualLastExecuteTime = null;
        this.currentExecuteTime = null;
        this.nextExecuteTime = isCronParamValid(jobInfo) ? new CronSequenceGenerator(getCronParam()).next(new Date()) : null;//下次执行时间为null代表只执行一次
    }


    public Long getDelayExecuteTime(Date endTime) {
        return nextExecuteTime.getTime() - endTime.getTime();
    }

    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    public Date getCurrentExecuteTime() {
        return currentExecuteTime;
    }

    public void setCurrentExecuteTime(Date currentExecuteTime) {
        this.currentExecuteTime = currentExecuteTime;
    }

    public Date getActualLastExecuteTime() {
        return actualLastExecuteTime;
    }

    public void setActualLastExecuteTime(Date actualLastExecuteTime) {
        this.actualLastExecuteTime = actualLastExecuteTime;
    }

    @Override
    public String toString() {
        return "JobInfoWrapper{" +
                "nextExecuteTime=" + nextExecuteTime +
                ", currentExecuteTime=" + currentExecuteTime +
                ", actualLastExecuteTime=" + actualLastExecuteTime +
                "} " + super.toString();
    }
}

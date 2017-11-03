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
     * 当前调用完成后做更新操作
     */
    public JobInfoWrapper updateExecuteTimes() {
        //// TODO: 11/2/17 更新下次执行时间、当前执行时间、实际执行时间
        actualLastExecuteTime = currentExecuteTime;
        currentExecuteTime = new Date();
        nextExecuteTime = new CronSequenceGenerator(getCronParam()).next(new Date());
        return this;
    }

    public Boolean canExecute(JobInfo jobInfo) {
        if (this.getCronParam().equals(jobInfo.getCronParam())) {
            return true;
        }
        // TODO: 11/3/17  判断执行机器
        return false;
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
}

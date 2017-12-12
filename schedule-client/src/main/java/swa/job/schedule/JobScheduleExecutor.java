package swa.job.schedule;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronSequenceGenerator;
import swa.job.ApplicationLoader;
import swa.job.JobInfo;
import swa.job.cronParser.JobInfoWrapper;
import swa.job.cronParser.ScheduleJobList;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by jinyan on 11/1/17 5:07 PM.
 */
public final class JobScheduleExecutor {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleExecutor.class);
    //保存当前可执行的任务信息
    private static final ConcurrentHashMap<Long, JobInfo> executingJobs = new ConcurrentHashMap<Long, JobInfo>();//当前需要执行的任务,只执行一次的任务不添加到此队列
    //根据下次执行时间做排序的任务列表
    private static final ScheduleJobList jobs = new ScheduleJobList();
    private static final AtomicBoolean isStarted = new AtomicBoolean(false);


    //启动执行
    private static void executeScheduledJobs() {
        if (!isStarted.get()) {
            isStarted.compareAndSet(true, false);
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        final JobInfoWrapper jobInfoWrapper = jobs.checkJob();
                        if (jobInfoWrapper == null) {
                            continue;
                        }
                        if (jobInfoWrapper.getNextExecuteTime() == null || !jobInfoWrapper.getNextExecuteTime().after(new Date())) {
                            JobInfoWrapper jobExecute = jobs.getJob();
                            executeNow(jobExecute);
                            JobInfoWrapper updated = updateJobWrapperInfo(jobExecute);
                            if (updated != null) {
                                jobs.addJob(updateJobWrapperInfo(jobExecute));
                            }
                        }
                    }
                }
            }).start();
        }
    }


    public static JobInfoWrapper updateJobWrapperInfo(JobInfoWrapper executedJobWrapper) {
        JobInfo jobInfo = executingJobs.get(executedJobWrapper.getJobId());
        if (jobInfo == null || !JobInfoWrapper.isCronParamValid(jobInfo)) {
            return null;
        }
        JobInfoWrapper result = JobInfoWrapper.newWrapperJob(jobInfo);
        result.setActualLastExecuteTime(new Date());
        result.setCurrentExecuteTime(executedJobWrapper.getNextExecuteTime());
        String cronParam = jobInfo.getCronParam();
        result.setNextExecuteTime(new CronSequenceGenerator(cronParam).next(executedJobWrapper.getNextExecuteTime()));//下次执行时间为null代表只执行一次
        return result;
    }


    /**
     * 首次添加任务
     *
     * @param jobInfoStr
     */
    public static void addRegisteredJob(String jobInfoStr) {
        JobInfo jobInfo = JSON.parseObject(jobInfoStr, JobInfo.class);
        if (JobInfoWrapper.isCronParamValid(jobInfo)) {
            executingJobs.put(jobInfo.getJobId(), jobInfo);
        }
        jobs.addJob(JobInfoWrapper.newWrapperJob(jobInfo));//将当前需要执行的任务添加到任务列表中
        if (!isStarted.get()) {
            executeScheduledJobs();
        }
    }


    private static void executeNow(JobInfo jobInfo) {
        try {
            if (null != ApplicationLoader.getBean(jobInfo.getBeanName())) {
                if (null != jobInfo.getParam() && jobInfo.getParam().trim() != null && jobInfo.getParam().trim().length() != 0) {
                    logger.debug("param:{}", jobInfo.getParam());
                    Method[] methods = ApplicationLoader.getBean(jobInfo.getBeanName()).getClass().getMethods();
                    for (Method method : methods) {
                        if (method.getName().equals(jobInfo.getMethodName())) {
                            method.invoke(ApplicationLoader.getBean(jobInfo.getBeanName()), jobInfo.getParam());
                        }
                    }
                } else {
                    Method method = ApplicationLoader.getBean(jobInfo.getBeanName()).getClass().getMethod(jobInfo.getMethodName());
                    method.invoke(ApplicationLoader.getBean(jobInfo.getBeanName()));
                }
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }


    public static void main(String[] args) {
        Method[] methods = JobScheduleExecutor.class.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

    }
}


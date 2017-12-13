package swa.job.schedule;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.support.CronSequenceGenerator;
import swa.job.ApplicationLoader;
import swa.job.JobInfo;
import swa.job.cronParser.JobInfoWrapper;
import swa.job.cronParser.ScheduleJobList;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by jinyan on 11/1/17 5:07 PM.
 */
public final class JobScheduleExecutor {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleExecutor.class);
    //保存当前可执行的任务信息
    //根据下次执行时间做排序的任务列表
    private static final Map<JobInfo, ScheduleJobList> jobMap = new ConcurrentHashMap<JobInfo, ScheduleJobList>();
    private static final AtomicBoolean isStarted = new AtomicBoolean(false);
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);


    //启动执行
    private static void executeScheduledJobs() {
        if (!isStarted.get()) {
            isStarted.compareAndSet(true, false);
            executor.submit(
                    new Runnable() {
                        public void run() {
                            {
                                while (true) {
                                    Collection<JobInfo> keys = jobMap.keySet();
                                    for (JobInfo jobInfo : keys) {
                                        ScheduleJobList list = jobMap.get(jobInfo);
//                                        logger.debug("list:{}", list);
//                                        logger.debug("map:{}", jobMap);

                                        final JobInfoWrapper jobInfoWrapper = list.checkJob();
                                        if (jobInfoWrapper == null) {
//                                            logger.debug("1");
                                            continue;
                                        }
                                        if (jobInfoWrapper.getNextExecuteTime() == null || !jobInfoWrapper.getNextExecuteTime().after(new Date())) {
                                            JobInfoWrapper jobExecute = list.getJob();
                                            executeNow(jobExecute);
                                            JobInfoWrapper updated = updateJobWrapperInfo(jobExecute);
//                                            logger.debug("getUpdate:{}", updated);
                                            if (updated != null) {
                                                list.addJob(updateJobWrapperInfo(jobExecute));
                                                jobMap.put(jobInfo, list);
//                                                logger.debug("addJob:{}", list);
                                            }
                                            if(list.checkJob()==null){
                                                jobMap.remove(jobInfo);
                                            }
//                                            logger.debug("2");
                                            continue;
                                        }

                                    }
                                }
                            }
                        }
                    });
        }
    }


    public static JobInfoWrapper updateJobWrapperInfo(JobInfoWrapper executedJobWrapper) {
        logger.debug("update:{}", executedJobWrapper);
        if (!JobInfoWrapper.isCronParamValid(executedJobWrapper)) {
            return null;
        }
        JobInfoWrapper result = JobInfoWrapper.newWrapperJob(executedJobWrapper);
        result.setActualLastExecuteTime(new Date());
        result.setCurrentExecuteTime(executedJobWrapper.getNextExecuteTime());
        String cronParam = executedJobWrapper.getCronParam();
        result.setNextExecuteTime(new CronSequenceGenerator(cronParam).next(executedJobWrapper.getNextExecuteTime()));
        return result;
    }


    /**
     * 首次添加任务
     *
     * @param jobInfoStr
     */
    public static void addRegisteredJob(String jobInfoStr) {
        logger.debug("addRegisteredJob-begin:{}", jobMap);
        JobScheduleParser.ScheduleMsgBody msg = JSON.parseObject(jobInfoStr, JobScheduleParser.ScheduleMsgBody.class);
        if ("ADD".equals(msg.getCode())) {
            ScheduleJobList scheduleJobList = new ScheduleJobList();
            scheduleJobList.addJob(JobInfoWrapper.newWrapperJob(msg.getNewJob()));//将当前需要执行的任务添加到任务列表中
            jobMap.put(msg.getNewJob(), scheduleJobList);
        } else if ("MODIFY".equals(msg.getCode())) {
            ScheduleJobList scheduleJobList = new ScheduleJobList();
            scheduleJobList.addJob(JobInfoWrapper.newWrapperJob(msg.getNewJob()));//将当前需要执行的任务添加到任务列表中
            jobMap.remove(msg.getOldJob());
            jobMap.put(msg.getNewJob(), scheduleJobList);
        } else {
            throw new SchedulingException("innvalid code");
        }
        logger.debug("addRegisteredJob-end:{}", jobMap);

        if (!isStarted.get()) {
            executeScheduledJobs();
        }
    }


    private static void executeNow(final JobInfo jobInfo) {
        if (null == jobInfo) {
            return;
        }
        executor.submit(new Runnable() {
            public void run() {
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
        });

    }


    public static void main(String[] args) {
        Method[] methods = JobScheduleExecutor.class.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

    }
}


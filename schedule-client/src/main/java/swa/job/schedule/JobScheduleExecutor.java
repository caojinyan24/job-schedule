package swa.job.schedule;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.job.ApplicationManager;
import swa.job.JobInfo;
import swa.job.cronParser.JobInfoWrapper;
import swa.job.cronParser.ScheduleJobList;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by jinyan on 11/1/17 5:07 PM.
 */
public class JobScheduleExecutor {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleExecutor.class);
    private static ConcurrentHashMap<Long, JobInfo> executingJobs = new ConcurrentHashMap<Long, JobInfo>();
    private static ScheduleJobList jobs = new ScheduleJobList();
    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
    private static AtomicBoolean isStarted = new AtomicBoolean(false);

    private static BlockingQueue<JobInfo> executeNowJobs = new LinkedBlockingQueue<JobInfo>();
    private static AtomicBoolean isExecuteNowStarted = new AtomicBoolean(false);

    /**
     * 当job新增或修改时，添加job
     * 根据id，替换掉目前队列中已有的
     *
     * @param jobInfoStr
     */
    public static void addJob(String jobInfoStr) {
        JobInfo jobInfo = JSON.parseObject(jobInfoStr, JobInfo.class);
        logger.debug("addJob：{}", jobInfo);
        if (null == jobInfo.getCronParam() || "".equals(jobInfo.getCronParam())) {
            executeNowJobs.add(jobInfo);
            startOnceJobs();
        } else {
//            JobInfo current = executingJobs.get(jobInfo.getJobId());
//            if (current != null) {
            executingJobs.put(jobInfo.getJobId(), jobInfo);
//            }
            jobs.addJob(jobInfo);
            startRepeatedJobs();

        }
    }

    private static void startOnceJobs() {
        if (!isExecuteNowStarted.get()) {
            isExecuteNowStarted.compareAndSet(true, false);
            while (true) {
                JobInfo jobInfo = executeNowJobs.poll();
                if (null != jobInfo) {
                    new JobScheduleExecutor().executeNow(jobInfo);
                }
            }
        }
    }

    public static void startRepeatedJobs() {
        if (!isStarted.get()) {
            isStarted.compareAndSet(true, false);
            while (true) {
                final JobInfoWrapper jobInfoWrapper = jobs.getJob();
                if (jobInfoWrapper != null) {

                    logger.debug("jobInfoWrapper:{}", jobInfoWrapper);
                    service.schedule(new Runnable() {
                        public void run() {
                            new JobScheduleExecutor().executeRepeat(jobInfoWrapper);
                        }
                    }, jobInfoWrapper.getDelayExecuteTime(new Date()), TimeUnit.MILLISECONDS);

                }
            }
        }
    }

    /**
     * 解析任务调度时间并开始执行
     * 执行前再次确认执行时间和执行机器地址是否发生变更
     *
     * @param jobInfoWrapper
     */
    private void executeRepeat(final JobInfoWrapper jobInfoWrapper) {
        //        this.localAddress = RemotingUtil.getLocalAddress();

        JobInfo jobInfo = executingJobs.get(jobInfoWrapper.getJobId());
        logger.debug("execute:{}", jobInfo);
        if (jobInfoWrapper.canExecute(jobInfo)) {//判断当前的jobInfo和下次执行时间是不是当前时间
            //todo 判断当前机器是否可执行    要求传的job中address不能为空
            logger.debug("aa");
            executeNow(jobInfoWrapper);
            jobs.addJob(jobInfoWrapper.updateExecuteTimes());
            logger.debug("executeRepeat:{}", jobs);
        } else {
            jobs.addJob(jobInfo);
            logger.debug("executeRepeat:{}", jobs);

        }
    }

    private void executeNow(JobInfo jobInfo) {
        logger.debug("bb");
        try {
            logger.debug("appliacton:{}", ApplicationManager.getBean(jobInfo.getBeanName()));

            if (null != ApplicationManager.getBean(jobInfo.getBeanName())) {
                logger.debug("cc");
                try {
                    Method method = ApplicationManager.getBean(jobInfo.getBeanName()).getClass().getMethod(jobInfo.getMethodName());
                    method.invoke(ApplicationManager.getBean(jobInfo.getBeanName()).getClass(), jobInfo.getParam());
                    // TODO: 11/3/17 执行完成后，向server端发送执行记录，保存在历史表中
                } catch (Exception e) {
//                throw new RuntimeException("方法调用失败", e);
                    logger.error("invoke error:", e);
                }
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }
}


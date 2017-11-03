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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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

    /**
     * 当job新增或修改时，添加job
     * 根据id，替换掉目前队列中已有的
     *
     * @param jobInfoStr
     */
    public static void addJob(String jobInfoStr) {
        JobInfo jobInfo = JSON.parseObject(jobInfoStr, JobInfo.class);
        JobInfo current = executingJobs.get(jobInfo.getJobId());
        if (current != null) {
            executingJobs.put(jobInfo.getJobId(), jobInfo);
        }
        jobs.addJob(jobInfo);
        start();
    }

    /**
     * 解析任务调度时间并开始执行
     * 执行前再次确认执行时间和执行机器地址是否发生变更
     *
     * @param jobInfoWrapper
     */
    private void startExecute(final JobInfoWrapper jobInfoWrapper) {
        //        this.localAddress = RemotingUtil.getLocalAddress();
        JobInfo jobInfo = executingJobs.get(jobInfoWrapper.getJobId());
        if (jobInfoWrapper.canExecute(jobInfo)) {//判断当前的jobInfo和下次执行时间是不是当前时间
            //todo 判断当前机器是否可执行    要求传的job中address不能为空
            if (null != ApplicationManager.getBean(jobInfo.getBeanName())) {
                try {
                    Method method = ApplicationManager.getBean(jobInfo.getBeanName()).getClass().getMethod(jobInfo.getMethodName());
                    method.invoke(ApplicationManager.getBean(jobInfo.getBeanName()).getClass(), jobInfo.getParam());
                } catch (Exception e) {
                    throw new RuntimeException("方法调用失败", e);
                }
            }
            jobs.addJob(jobInfoWrapper.updateExecuteTimes());
        } else {
            jobs.addJob(jobInfo);
        }
    }

    public static void start() {
        if (!isStarted.get()) {
            isStarted.compareAndSet(true, false);
            while (true) {
                final JobInfoWrapper jobInfoWrapper = jobs.getJob();
                service.schedule(new Runnable() {
                    public void run() {
                        new JobScheduleExecutor().startExecute(jobInfoWrapper);
                    }
                }, jobInfoWrapper.getDelayExecuteTime(new Date()), TimeUnit.MILLISECONDS);
            }
        }
    }
}


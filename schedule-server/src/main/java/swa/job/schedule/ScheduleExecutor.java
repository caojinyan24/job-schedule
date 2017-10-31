package swa.job.schedule;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swa.db.entity.JobInfo;
import swa.db.entity.ScheduleHistory;
import swa.db.mapper.ScheduleHistoryMapper;
import swa.db.service.ScheduleService;
import swa.job.common.JobContext;
import swa.rpc.Client;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度的核心类
 * Created by jinyan on 10/20/17 3:42 PM.
 */
@Service
public class ScheduleExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleExecutor.class);

    @Resource
    private ScheduleService scheduleService;
    @Resource
    private CronParserService cronParserService;
    @Resource
    private ScheduleHistoryMapper scheduleHistoryMapper;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(20);
    // TODO: 10/21/17 根据Date做排序，而不是通过key的hash值
    private LinkedBlockingDeque<JobContext> jobContexts = new LinkedBlockingDeque<JobContext>();//存放job名称和下次执行时间，每次有新的job加入或job做了修改的时候，都需要把job加入map中


    // TODO: 10/30/17
    @PostConstruct
    public void setUp() {
//        while (true) {
        if (!jobContexts.isEmpty()) {
            schedule(jobContexts.poll().getJobCode());
        }
//        }
    }

    /**
     * springschedule的任务调度如果需要修改调度时间，只能修改代码后重启服务；
     * 为了避免服务重启，需要在每次执行任务之前检查任务的调度时间是否更改：即重新计算下次执行时间
     *
     * @param jobCode
     */

    public void schedule(final Integer jobCode) {
        logger.info("schedule job:{}", jobCode);
        //获取最新的调度时间
        final JobContext jobContext = scheduleService.getExecuteJobInfo(jobCode);
        Date nextScheduleTime = cronParserService.getNextScheduleTime(jobContext.getCronParam());
        final JobInfo jobInfo = new JobInfo(jobContext.getJobCode(), jobContext.getAppName(), jobContext.getBeanName(), jobContext.getMethodName());
        //执行调度
        executorService.schedule(new Runnable() {
            public void run() {
                try {
                    new Client(jobContext.getAddress(), jobContext.getPort(), JSON.toJSONString(jobInfo)).start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scheduleHistoryMapper.add(new ScheduleHistory(jobCode, jobContext.getAddress() + ":" + jobContext.getPort(), jobContext.getParam(), new Date()));// TODO: 10/23/17 获取请求结果
                jobContexts.add(jobContext);
            }
        }, getDelayedTime(nextScheduleTime), TimeUnit.SECONDS);

    }

    private long getDelayedTime(Date nextScheduleTime) {
        long delayTimeInSeconds = (nextScheduleTime.getTime() - new Date().getTime()) / 1000;
        if (delayTimeInSeconds < 0) {
            logger.info("调度时间有误");
        }
        return 0;
    }
}

package swa.job.cronParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.job.JobInfo;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by jinyan on 11/2/17 3:31 PM.
 */
public class ScheduleJobList{
    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobList.class);
    private static PriorityBlockingQueue<JobInfoWrapper> list;

    public ScheduleJobList() {
        list = new PriorityBlockingQueue<JobInfoWrapper>(10, new Comparator<JobInfoWrapper>() {
            public int compare(JobInfoWrapper o1, JobInfoWrapper o2) {
                if (o1.nextExecuteTime.before(o2.nextExecuteTime)) {
                    return 1;
                } else if (o1.nextExecuteTime.equals(o2.nextExecuteTime)) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }

    public JobInfoWrapper getJob() {
        return list.poll();
    }

    /**
     * 初次加入新任务
     * @param jobInfo
     */
    public void addJob(JobInfo jobInfo) {
        JobInfoWrapper jobInfoWrapper=new JobInfoWrapper();
        // TODO: 11/3/17 组装
        list.add(jobInfoWrapper);
    }

    /**
     * 执行后将下次待执行的任务加入执行列表
     * @param jobInfoWrapper
     */
    public void addJob(JobInfoWrapper jobInfoWrapper) {
        list.add(jobInfoWrapper);
    }
}

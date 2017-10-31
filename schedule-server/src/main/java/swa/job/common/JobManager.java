package swa.job.common;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by jinyan on 10/23/17 4:44 PM.
 */
public class JobManager {
    private static final Logger logger = LoggerFactory.getLogger(JobManager.class);
    private static List<JobContext> jobPool = Lists.newArrayList();//维护当前所有的job列表

    public static JobContext getToScheduleJob() {
        // TODO: 10/23/17 获取当前要执行的job,需要判断下次执行时间
        return new JobContext();
    }

}

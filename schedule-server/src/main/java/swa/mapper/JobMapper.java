package swa.mapper;

import swa.job.schedule.dto.JobContext;

/**
 * Created by jinyan on 10/20/17 3:57 PM.
 */
public interface JobMapper {
    JobContext selectByJobName(String jobName);
    void insertJobScheduleHistory(String jobName,Boolean executeStatus);

}

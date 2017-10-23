package swa.mapper;


import org.apache.ibatis.annotations.Param;
import swa.job.common.entity.Job;
import swa.spring.JobContext;

import java.util.List;

/**
 * Created by jinyan on 10/20/17 3:57 PM.
 */
public interface JobMapper {
    JobContext selectByJobName(String jobName);

    void insertJobs(@Param("jobs") List<JobContext> jobs);

    public void add(Job job);

    public void update(Job job);

    public void deleteByPriKey(Long id);

    public Job queryByPriKey(Long id);

}

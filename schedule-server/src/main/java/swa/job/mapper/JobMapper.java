package swa.job.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import swa.job.common.entity.Job;
import swa.job.common.entity.JobContext;

import java.util.List;

/**
 * Created by jinyan on 10/20/17 3:57 PM.
 */
@Repository
public interface JobMapper {
    JobContext selectByJobName(@Param("jobName") String jobName);

    void insertJobs(@Param("jobs") List<JobContext> jobs);

    public void add(Job job);

    public void update(Job job);

    public void deleteByPriKey(Long id);

    public Job queryByPriKey(Long id);

}

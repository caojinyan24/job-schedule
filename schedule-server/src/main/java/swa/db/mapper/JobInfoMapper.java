package swa.db.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import swa.db.entity.JobInfo;

import java.util.List;

/**
 * Created by jinyan on 10/20/17 3:57 PM.
 */
@Repository
public interface JobInfoMapper {

    JobInfo selectByJobId(@Param("jobId") Long jobId);

    void updateJobInfo(JobInfo jobInfo);


    void insertJob(JobInfo jobs);


    List<JobInfo> selectSelective(JobInfo jobInfo);


}

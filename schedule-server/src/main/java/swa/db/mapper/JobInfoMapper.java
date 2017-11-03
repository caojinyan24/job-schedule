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
    List<JobInfo> selectByJobCode(@Param("jobCode") Integer jobCode);
    JobInfo selectByJobId(@Param("jobId") Long jobId);


    void insertJob(@Param("jobInfo") JobInfo jobs);



}

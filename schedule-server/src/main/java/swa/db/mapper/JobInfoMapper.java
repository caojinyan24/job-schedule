package swa.db.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import swa.db.entity.JobInfo;

/**
 * Created by jinyan on 10/20/17 3:57 PM.
 */
@Repository
public interface JobInfoMapper {
    JobInfo selectByJobCode(@Param("jobCode") Integer jobCode);

    Boolean isExist(@Param("appName") String appName, @Param("beanName") String beanName, @Param("methodName") String methodName);

    void insertJob(@Param("jobInfo") JobInfo jobs);

//    public void add(JobInfo job);
//
//    public void update(JobInfo job);
//
//    public void deleteByPriKey(Long id);
//
//    public JobInfo queryByPriKey(Long id);

}

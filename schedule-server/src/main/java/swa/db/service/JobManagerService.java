package swa.db.service;

import swa.db.entity.JobInfo;
import swa.job.common.JobContext;

/**
 * Created by jinyan on 11/1/17 3:35 PM.
 */
public interface JobManagerService {
    Boolean isExist(String appName, String beanName, String methodName);

    Integer generateJobCode(String appName, String beanName, String methodName);

    JobContext getExecuteJobInfo(Long jobId);

    void saveJobInfo(JobInfo jobInfo);


}

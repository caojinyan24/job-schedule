package swa.db.service;

import swa.job.common.JobContext;

/**
 * Created by jinyan on 10/12/17 3:12 PM.
 */
public interface ScheduleService {

    void saveJobInfo(String paramStr);

    JobContext getExecuteJobInfo(Integer jobCode);
}

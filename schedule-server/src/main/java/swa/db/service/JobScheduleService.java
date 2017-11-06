package swa.db.service;

import swa.job.common.JobContext;

/**
 * Created by jinyan on 11/6/17 6:33 PM.
 */
public interface JobScheduleService {
    void addJobScheduleHistory(JobContext jobInfo);
}

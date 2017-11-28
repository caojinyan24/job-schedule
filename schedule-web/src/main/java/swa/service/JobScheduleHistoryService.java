package swa.service;


import swa.db.entity.ScheduleHistory;

import java.util.List;

/**
 * Created by jinyan on 11/28/17 10:56 AM.
 */
public interface JobScheduleHistoryService {
    List<ScheduleHistory> queryByJobId(Long jobId);
}

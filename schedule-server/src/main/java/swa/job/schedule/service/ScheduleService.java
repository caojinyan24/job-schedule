package swa.job.schedule.service;

import swa.job.schedule.dto.ScheduleResponse;

/**
 * Created by jinyan on 10/12/17 3:12 PM.
 */
public interface ScheduleService {
    ScheduleResponse schedule(String jobName, String ipAddr);
}

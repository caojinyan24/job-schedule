package swa.job.schedule.service;

import java.util.Date;

/**
 * Created by jinyan on 10/20/17 2:26 PM.
 */
public interface CronParserService {
    Date getNextScheduleTime(String cronParam);
}

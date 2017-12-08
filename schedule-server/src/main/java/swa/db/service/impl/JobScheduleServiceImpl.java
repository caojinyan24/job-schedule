package swa.db.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swa.db.entity.ScheduleHistory;
import swa.db.mapper.ScheduleHistoryMapper;
import swa.db.service.JobScheduleService;
import swa.job.common.JobContext;
import swa.util.ScheduleStatusEnum;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by jinyan on 11/6/17 6:32 PM.
 */
@Service
public class JobScheduleServiceImpl implements JobScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleServiceImpl.class);
    @Resource
    private ScheduleHistoryMapper scheduleHistoryMapper;

    public void addJobScheduleHistory(JobContext jobInfo,ScheduleStatusEnum scheduleStatus) {
        ScheduleHistory scheduleHistory = new ScheduleHistory(jobInfo.getJobId(), jobInfo.getAddress(), jobInfo.getParam(), new Date());
        scheduleHistory.setExecuteStatus(scheduleStatus);
        scheduleHistoryMapper.insertHistory(scheduleHistory);
    }
}

package swa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swa.db.entity.ScheduleHistory;
import swa.db.mapper.ScheduleHistoryMapper;
import swa.service.JobScheduleHistoryService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jinyan on 11/28/17 10:56 AM.
 */
@Service
public class JobScheduleHistoryServiceImpl implements JobScheduleHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleHistoryServiceImpl.class);
    @Resource
    private ScheduleHistoryMapper scheduleHistoryMapper;

    public List<ScheduleHistory> queryByJobId(Long jobId) {
        return scheduleHistoryMapper.queryByJobId(jobId);
    }
}

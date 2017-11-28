package swa.db.mapper;


import org.springframework.stereotype.Repository;
import swa.db.entity.ScheduleHistory;

import java.util.List;

/**
 * ScheduleHistoryMapper
 * Created by jinyan.cao on 2017-10-23 18:37:55
 */
@Repository
public interface ScheduleHistoryMapper {

     void insertHistory(ScheduleHistory scheduleHistory);
//
//    public void update(ScheduleHistory scheduleHistory);
//
//    public void deleteByPriKey(Long id);
//
     List<ScheduleHistory> queryByJobId(Long jobId);

}
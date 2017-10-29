package swa.job.mapper;


import org.springframework.stereotype.Repository;
import swa.job.common.entity.ScheduleHistory;

/**
 * ScheduleHistoryMapper
 * Created by jinyan.cao on 2017-10-23 18:37:55
 */
@Repository
public interface ScheduleHistoryMapper {

    public void add(ScheduleHistory scheduleHistory);

    public void update(ScheduleHistory scheduleHistory);

    public void deleteByPriKey(Long id);

    public ScheduleHistory queryByPriKey(Long id);

}
package swa.job.schedule.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronSequenceGenerator;
import swa.job.schedule.service.CronParserService;

import java.util.Date;

/**
 * Created by jinyan on 10/20/17 2:26 PM.
 */
public class CronParserServiceImpl implements CronParserService {
    private static final Logger logger = LoggerFactory.getLogger(CronParserServiceImpl.class);

    public Date getNextScheduleTime(String cronParam) {
        return new CronSequenceGenerator(cronParam).next(new Date());//暂时只从当前时间计算下一次执行时间
    }
}

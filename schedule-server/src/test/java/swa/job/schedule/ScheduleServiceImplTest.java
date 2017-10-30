package swa.job.schedule;

import org.junit.Test;
import swa.BaseTest;

import javax.annotation.Resource;

/**
 * Created by jinyan on 10/30/17 7:02 PM.
 */
public class ScheduleServiceImplTest extends BaseTest {
    @Resource
    private ScheduleService scheduleService;

    @Test
    public void testProcessJobInfo() throws Exception {
        String str = "{\"address\":\"\",\"beanName\":\"dataLoaderServiceImpl\",\"jobName\":\"hot.deploy.test\",\"methodName\":\"scheduleTest\"}";
//        jobInfoReceiver.channelRead(null, );
        scheduleService.processJobInfo(str);


    }
}
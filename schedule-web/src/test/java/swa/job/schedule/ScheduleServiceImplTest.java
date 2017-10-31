package swa.job.schedule;

import org.junit.Test;
import swa.BaseTest;
import swa.db.service.impl.ScheduleServiceImpl;

import javax.annotation.Resource;

/**
 * Created by jinyan on 10/31/17 2:11 PM.
 */
public class ScheduleServiceImplTest extends BaseTest {
    @Resource
    private ScheduleServiceImpl scheduleServiceImpl;

    @Test
    public void testProcessJobInfo() throws Exception {
        String str = "{\"address\":\"\",\"beanName\":\"dataLoaderServiceImpl\",\"jobName\":\"hot.deploy.test\",\"methodName\":\"scheduleTest\"}";
        scheduleServiceImpl.saveJobInfo(str);


    }


}
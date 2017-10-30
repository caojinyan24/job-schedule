import org.junit.Test;
import swa.BaseTest;
import swa.job.schedule.ScheduleService;

import javax.annotation.Resource;

/**
 * Created by jinyan on 10/30/17 6:21 PM.
 */
public class JobInfoReceiverTest extends BaseTest {
//    @Resource
//    private JobInfoReceiver jobInfoReceiver;
    @Resource
    private ScheduleService scheduleService;

    @Test
    public void testChannelRead() throws Exception {
        String str="{\"address\":\"\",\"beanName\":\"dataLoaderServiceImpl\",\"jobName\":\"hot.deploy.test\",\"methodName\":\"scheduleTest\"}";
//        jobInfoReceiver.channelRead(null, );
        scheduleService.processJobInfo(str);



    }
}
package swa.job.schedule;

import org.junit.Test;
import swa.BaseTest;

import javax.annotation.Resource;

/**
 * Created by jinyan on 12/8/17 11:08 AM.
 */
public class JobExecutorTest extends BaseTest {
    @Resource
    private JobExecutor jobExecutor;

    @Test
    public void sendJob() throws Exception {
        jobExecutor.sendJob(1L);
    }

}
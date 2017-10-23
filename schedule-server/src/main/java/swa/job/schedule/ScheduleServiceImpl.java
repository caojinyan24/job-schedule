package swa.job.schedule;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import swa.mapper.JobMapper;
import swa.spring.JobContext;

import javax.annotation.Resource;
import java.util.List;

/**
 * 从库中获取定时任务执行时间，并根据时间发送任务调度请求
 * Created by jinyan on 10/12/17 3:10 PM.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);
    @Resource
    private JobMapper jobMapper;

    public String processJobInfo(String jobStr) {
        if (Strings.isNullOrEmpty(jobStr)) {
            return "innvalid msg";
        } else {
            List<JobContext> jobContexts = JSON.parseArray(jobStr, JobContext.class);
            if (!CollectionUtils.isEmpty(jobContexts)) {
                jobMapper.insertJobInfo(jobContexts);
                return "success";
            } else {
                return "innvalid msg";
            }
        }
    }
}

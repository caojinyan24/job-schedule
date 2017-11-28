package swa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swa.db.entity.JobInfo;
import swa.db.mapper.JobInfoMapper;
import swa.exception.JobScheduleException;
import swa.service.JobService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jinyan on 11/27/17 9:03 PM.
 */
@Service
public class JobServiceImpl implements JobService {
    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    @Resource
    private JobInfoMapper jobInfoMapper;

    public List<JobInfo> queryByAppName(String appName) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setAppName(appName);
        return jobInfoMapper.selectSelective(jobInfo);
    }

    public JobInfo queryById(Long jobId) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setId(jobId);
        List<JobInfo> jobInfos = jobInfoMapper.selectSelective(jobInfo);
        if (jobInfos.size() != 1) {
            throw new JobScheduleException("not uniq job");
        }
        return jobInfos.get(0);
    }
}

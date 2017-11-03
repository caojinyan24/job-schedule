package swa.db.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import swa.db.entity.ApplicationInfo;
import swa.db.entity.JobInfo;
import swa.db.mapper.ApplicationInfoMapper;
import swa.db.mapper.JobInfoMapper;
import swa.db.service.JobManagerService;
import swa.exception.JobScheduleException;
import swa.job.common.JobContext;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * Created by jinyan on 11/1/17 3:35 PM.
 */
@Service
public class JobManageServiceImpl implements JobManagerService {
    private static final Logger logger = LoggerFactory.getLogger(JobManageServiceImpl.class);
    @Resource
    private JobInfoMapper jobInfoMapper;
    @Resource
    private ApplicationInfoMapper applicationInfoMapper;

    public Boolean isExist(String appName, String beanName, String methodName) {
        Integer hashCode = generateJobCode(appName, beanName, methodName);
        List<JobInfo> jobInfos = jobInfoMapper.selectByJobCode(hashCode);
        if (CollectionUtils.isEmpty(jobInfos)) {
            return false;
        }
        for (JobInfo jobInfo : jobInfos) {
            if (jobInfo.getAppName().equals(appName) && jobInfo.getBeanName().equals(beanName) && jobInfo.getMethodName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    public Integer generateJobCode(String appName, String beanName, String methodName) {
        return Joiner.on(".").join(appName, beanName, methodName).hashCode();
    }

    public JobContext getExecuteJobInfo(Long jobId) {
        JobInfo jobInfo = jobInfoMapper.selectByJobId(jobId);
        ApplicationInfo applicationInfo = applicationInfoMapper.selectByAppName(jobInfo.getAppName());
        String address = "";
        if (Strings.isNullOrEmpty(jobInfo.getScheduleAddr())) {
            if (Strings.isNullOrEmpty(applicationInfo.getAddress())) {
                throw new JobScheduleException("server address havn't configged");
            } else {
                List<String> addrs = Splitter.on(",").splitToList(applicationInfo.getAddress());
                address = addrs.get(new Random().nextInt() % addrs.size());
                address += ":" + applicationInfo.getPort();
            }
        }
        return new JobContext(jobInfo, address, applicationInfo.getPort());
    }

    public void saveJobInfo(JobInfo jobInfo) {
        jobInfoMapper.updateJobInfo(jobInfo);
    }


}

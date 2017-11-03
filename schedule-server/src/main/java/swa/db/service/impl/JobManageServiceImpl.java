package swa.db.service.impl;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import swa.db.entity.JobInfo;
import swa.db.mapper.JobInfoMapper;
import swa.db.service.JobManagerService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jinyan on 11/1/17 3:35 PM.
 */
@Service
public class JobManageServiceImpl implements JobManagerService {
    private static final Logger logger = LoggerFactory.getLogger(JobManageServiceImpl.class);
    @Resource
    private JobInfoMapper jobInfoMapper;

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

}

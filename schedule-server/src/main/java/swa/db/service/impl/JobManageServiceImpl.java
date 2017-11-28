package swa.db.service.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import swa.db.entity.App;
import swa.db.entity.JobInfo;
import swa.db.mapper.AppMapper;
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
    private AppMapper appMapper;

    public Boolean isExist(String appName, String beanName, String methodName) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setAppName(appName);
        jobInfo.setBeanName(beanName);
        jobInfo.setMethodName(methodName);
        List<JobInfo> dbData = jobInfoMapper.selectSelective(jobInfo);
        return !CollectionUtils.isEmpty(dbData);
    }


    public JobContext getExecuteJobInfo(Long jobId) {
        JobInfo jobInfo = jobInfoMapper.selectByJobId(jobId);
        if(jobInfo==null){
            throw new JobScheduleException("no job found");
        }
        App applicationInfo = appMapper.selectByAppName(jobInfo.getAppName());
        String address = "";
        if (Strings.isNullOrEmpty(jobInfo.getScheduleAddr())) {
            if (Strings.isNullOrEmpty(applicationInfo.getAddress())) {
                throw new JobScheduleException("server address havn't configged");
            } else {
                List<String> addrs = Splitter.on(",").splitToList(applicationInfo.getAddress());
                address = addrs.get(new Random().nextInt() % addrs.size());
            }
        }
        return new JobContext(jobInfo, address, applicationInfo.getPort());
    }

    public void saveJobInfo(JobInfo jobInfo) {
        jobInfoMapper.updateJobInfo(jobInfo);
    }



}

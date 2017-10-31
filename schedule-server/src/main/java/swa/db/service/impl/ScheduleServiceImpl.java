package swa.db.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import swa.db.entity.ApplicationInfo;
import swa.db.entity.JobInfo;
import swa.db.mapper.ApplicationInfoMapper;
import swa.db.mapper.JobInfoMapper;
import swa.db.service.ScheduleService;
import swa.exception.JobScheduleException;
import swa.exception.PreconditionUtil;
import swa.job.common.JobContext;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 从库中获取定时任务执行时间，并根据时间发送任务调度请求
 * Created by jinyan on 10/12/17 3:10 PM.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);
    @Resource
    private JobInfoMapper jobInfoMapper;
    @Resource
    private ApplicationInfoMapper applicationInfoMapper;

    /**
     * 组装并保存任务信息到数据库
     * 取hashcode做appcode
     *
     * @param jobInfoStr
     */
    public void saveJobInfo(String jobInfoStr) {
        Map<String, Object> map = JSON.parseObject(jobInfoStr, Map.class);
        PreconditionUtil.check(null != map, "innvalid jobInfo");
        String appName = (String) map.get("appName");
        PreconditionUtil.check(!Strings.isNullOrEmpty(appName), "appName is empty");
        String beanName = (String) map.get("beanName");
        PreconditionUtil.check(!Strings.isNullOrEmpty(beanName), "beanName is empty");
        String methodName = (String) map.get("methodName");
        PreconditionUtil.check(!Strings.isNullOrEmpty(methodName), "methodName is empty");
        Integer port = (Integer) map.get("port");
        PreconditionUtil.check(null != port, "port is empty");
        if (!jobInfoMapper.isExist(appName, beanName, methodName)) {
            String joinName = Joiner.on(".").join(appName, beanName, methodName);
            int hashCode = joinName.hashCode();
            JobInfo jobInfo = new JobInfo(hashCode, appName, beanName, methodName);
            try {
                jobInfoMapper.insertJob(jobInfo);
            } catch (DuplicateKeyException e) {
                logger.info("same job exists");
            }
        }

        ApplicationInfo applicationInfo = applicationInfoMapper.selectByAppName(appName);
        if (applicationInfo == null || !applicationInfo.getPort().equals(port)) {
            applicationInfoMapper.insertOrUpdateApplicationInfo(applicationInfo);
        }

    }

    public JobContext getExecuteJobInfo(Integer jobCode) {
        JobInfo jobInfo = jobInfoMapper.selectByJobCode(jobCode);
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
}

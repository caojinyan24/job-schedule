package swa.job.registry;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import swa.db.entity.ApplicationInfo;
import swa.db.entity.JobInfo;
import swa.db.mapper.ApplicationInfoMapper;
import swa.db.mapper.JobInfoMapper;
import swa.db.service.JobManagerService;
import swa.exception.PreconditionUtil;
import swa.job.schedule.JobExecutor;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by jinyan on 11/3/17 5:55 PM.
 */
@Service
public class JobRegister {
    private static final Logger logger = LoggerFactory.getLogger(JobRegister.class);

    @Resource
    private JobInfoMapper jobInfoMapper;
    @Resource
    private ApplicationInfoMapper applicationInfoMapper;
    @Resource
    private JobManagerService jobManagerService;
    @Resource
    private JobExecutor scheduleExecutor;

    /**
     * 组装并保存任务信息到数据库
     * 取hashcode做appcode
     *
     * @param jobInfoStr
     */
    private void saveJobInfo(String jobInfoStr) {
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
        if (!jobManagerService.isExist(appName, beanName, methodName)) {
            JobInfo jobInfo = new JobInfo(jobManagerService.generateJobCode(appName, beanName, methodName),
                    appName, beanName, methodName);
            try {
                jobInfoMapper.insertJob(jobInfo);
            } catch (DuplicateKeyException e) {
                logger.info("same job exists");
            }
        }

        ApplicationInfo applicationInfo = applicationInfoMapper.selectByAppName(appName);
        if (applicationInfo == null) {
            applicationInfo = new ApplicationInfo();
            applicationInfo.setAppName(appName);
        }
        if (!port.equals(applicationInfo.getPort())) {
            applicationInfo.setAddress("127.0.0.1");//todo 后边改成从页面修改
            applicationInfo.setPort(port);
            applicationInfoMapper.insertOrUpdateApplicationInfo(applicationInfo);
        }
    }

    public void registerJob(String jobInfoStr) {
        JobInfo jobInfo = JSON.parseObject(jobInfoStr, JobInfo.class);
        saveJobInfo(jobInfoStr);
        jobInfo = jobInfoMapper.selectByUniqKey(jobInfo.getAppName(), jobInfo.getBeanName(), jobInfo.getMethodName());
        scheduleExecutor.sendJob(jobInfo.getId());
    }

}

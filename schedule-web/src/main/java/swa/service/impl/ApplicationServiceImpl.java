package swa.service.impl;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import swa.db.entity.ApplicationInfo;
import swa.db.mapper.ApplicationInfoMapper;
import swa.exception.PreconditionUtil;
import swa.service.ApplicationService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jinyan on 11/8/17 5:43 PM.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);
    @Resource
    private ApplicationInfoMapper applicationInfoMapper;

    public List<ApplicationInfo> queryApplicationInfo() {
        return applicationInfoMapper.queryApplicationInfos();
    }

    public ApplicationInfo queryByAppName(String appName) {
        PreconditionUtil.check(!Strings.isNullOrEmpty(appName),"appName is empty");
        return applicationInfoMapper.selectByAppName(appName);
    }
}

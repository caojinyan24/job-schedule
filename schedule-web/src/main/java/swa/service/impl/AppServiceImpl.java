package swa.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import swa.db.entity.App;
import swa.db.mapper.AppMapper;
import swa.exception.PreconditionUtil;
import swa.service.AppService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jinyan on 11/8/17 5:43 PM.
 */
@Service
public class AppServiceImpl implements AppService {
    @Resource
    private AppMapper appMapper;

    public List<App> queryApp() {
        return appMapper.queryApps();
    }

    public App queryByName(String appName) {
        PreconditionUtil.check(!Strings.isNullOrEmpty(appName),"appName is empty");
        return appMapper.selectByAppName(appName);
    }

    public Integer update(App app) {
        return appMapper.update(app);
    }
}

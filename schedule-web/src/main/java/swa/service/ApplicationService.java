package swa.service;

import swa.db.entity.ApplicationInfo;

import java.util.List;

/**
 * Created by jinyan on 11/8/17 5:44 PM.
 */
public interface ApplicationService {
    List<ApplicationInfo> queryApplicationInfo();
    ApplicationInfo queryByAppName(String appName);

}

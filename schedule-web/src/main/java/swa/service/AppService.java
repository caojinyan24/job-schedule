package swa.service;

import swa.db.entity.App;

import java.util.List;

/**
 * Created by jinyan on 11/8/17 5:44 PM.
 */
public interface AppService {
    List<App> queryApp();
    App queryByName(String appName);
    Integer update(App app);

}

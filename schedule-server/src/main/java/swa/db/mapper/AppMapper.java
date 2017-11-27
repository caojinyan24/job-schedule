package swa.db.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import swa.db.entity.App;

import java.util.List;

/**
 * ApplicationInfoMapper
 * Created by jinyan.cao on 2017-10-23 18:37:54
 */
@Repository
public interface AppMapper {

    List<App> queryApps();

    //
     Integer update(App app);
//
//    public void deleteByPriKey(Long id);
//
    App selectByAppName(@Param("appName") String appName);

    void insertOrUpdate(App app);

}
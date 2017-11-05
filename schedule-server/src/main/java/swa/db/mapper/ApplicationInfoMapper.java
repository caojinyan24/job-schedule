package swa.db.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import swa.db.entity.ApplicationInfo;

/**
 * ApplicationInfoMapper
 * Created by jinyan.cao on 2017-10-23 18:37:54
 */
@Repository
public interface ApplicationInfoMapper {

    //    public void add(ApplicationInfo ApplicationInfo);
//
//    public void update(ApplicationInfo ApplicationInfo);
//
//    public void deleteByPriKey(Long id);
//
    ApplicationInfo selectByAppName(@Param("appName") String appName);

    void insertOrUpdateApplicationInfo(ApplicationInfo applicationInfo);

}
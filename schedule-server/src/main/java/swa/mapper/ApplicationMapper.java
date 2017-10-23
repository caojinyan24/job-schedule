package swa.mapper;


import swa.job.common.entity.Application;

/**
* ApplicationMapper
* Created by jinyan.cao on 2017-10-23 18:37:54
*/
public interface ApplicationMapper{

    public void add(Application application);

    public void update(Application application);

    public void deleteByPriKey(Long id);

    public Application queryByPriKey(Long id);

}
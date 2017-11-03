package swa.db.service;

/**
 * Created by jinyan on 11/1/17 3:35 PM.
 */
public interface JobManagerService {
    Boolean isExist(String appName,String beanName,String methodName);
    Integer generateJobCode(String appName,String beanName,String methodName);

}

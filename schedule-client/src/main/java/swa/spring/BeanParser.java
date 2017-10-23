package swa.spring;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import swa.rpc.Client;

import java.lang.reflect.Method;

/**
 * Created by jinyan on 10/11/17 9:53 PM.
 */
public class BeanParser implements BeanPostProcessor {
    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (null != method.getAnnotation(JobSchedule.class)) {
                JobContext jobContext = new JobContext();
                jobContext.setAddress("");// TODO: 10/23/17 获取本机ip地址
                jobContext.setBeanName(beanName);
                jobContext.setMethodName(method.getName());
                jobContext.setJobName(method.getAnnotation(JobSchedule.class).jobName());
                try {
                    new Client("127.0.0.1", 8087, JSON.toJSONString(jobContext)).start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}

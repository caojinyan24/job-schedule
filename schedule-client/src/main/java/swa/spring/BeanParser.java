package swa.spring;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import swa.rpc.Client;
import swa.rpc.JobContext;

import java.lang.reflect.Method;

/**
 * Created by jinyan on 10/11/17 9:53 PM.
 */
public class BeanParser implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(BeanParser.class);
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (null != method.getAnnotation(JobSchedule.class)) {
                final JobContext jobContext = new JobContext();
                jobContext.setAddress("");// TODO: 10/23/17 获取本机ip地址
                jobContext.setBeanName(beanName);
                jobContext.setMethodName(method.getName());
                jobContext.setJobName(method.getAnnotation(JobSchedule.class).jobName());

                new Runnable() {
                    public void run() {
                        try {
                            new Client("127.0.0.1", 8087, JSON.toJSONString(jobContext)).start();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.run();
                logger.info("init done");
            }
        }
        return bean;
    }
}

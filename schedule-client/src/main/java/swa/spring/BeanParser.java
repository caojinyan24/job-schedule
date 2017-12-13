package swa.spring;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import swa.job.JobInfo;
import swa.job.register.Client;

import java.lang.reflect.Method;

/**
 * Created by jinyan on 10/11/17 9:53 PM.
 */
public class BeanParser implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(BeanParser.class);
    private static String APP_NAME;
    private static Integer PORT;

    public static void setPORT(Integer PORT) {
        BeanParser.PORT = PORT;
    }

    public static void setAppName(String appName) {
        APP_NAME = appName;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (null != method.getAnnotation(JobSchedule.class)) {
                final JobInfo jobInfo = new JobInfo();
                jobInfo.setBeanName(beanName);
                jobInfo.setMethodName(method.getName());
                jobInfo.setAppName(APP_NAME);
                jobInfo.setPort(PORT);
                logger.info("send info:{}", JSON.toJSONString(jobInfo));
                new Runnable() {
                    public void run() {
                        try {
                            new Client("127.0.0.1", 8087, jobInfo).start();//// TODO: 10/31/17 schedule服务端开放的服务注册地址和端口，后期考虑优化
                        } catch (InterruptedException e) {
                            logger.error("client start error:", e);
                        }
                    }
                }.run();
                logger.info("init done");
            }
        }
        return bean;
    }

}

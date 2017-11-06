package swa.job;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by jinyan on 10/30/17 1:44 PM.
 */
public class ApplicationLoader implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }



    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationLoader.applicationContext = applicationContext;
    }

}

package swa.job.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by jinyan on 10/31/17 12:21 PM.
 */
@Component
public class ApplicationManager implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationManager.class);
    private static ApplicationContext applicationContext;

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class beanClass) {
        return (T) applicationContext.getBean(beanClass);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationManager.applicationContext = applicationContext;
    }
}

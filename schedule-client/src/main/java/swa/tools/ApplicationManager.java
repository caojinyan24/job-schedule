package swa.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by jinyan on 10/30/17 1:44 PM.
 */
public class ApplicationManager<T> implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationManager.class);
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ApplicationManager() {
        logger.info("set application done:{}", applicationContext);

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationManager.applicationContext = applicationContext;
        logger.info("set application done:{}", applicationContext);
    }

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class beanClass) {
        return (T) applicationContext.getBean(beanClass);
    }
}

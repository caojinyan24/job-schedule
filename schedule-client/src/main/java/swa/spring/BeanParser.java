package swa.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * Created by jinyan on 10/11/17 9:53 PM.
 */
public class BeanParser implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(BeanParser.class);

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Method[]methods=bean.getClass().getMethods();
        for(Method method:methods){
            if(null!=method.getAnnotation(JobSchedule.class)){
                //向服务端发送job名称，method方法
            }
        }
        return bean;
    }
}

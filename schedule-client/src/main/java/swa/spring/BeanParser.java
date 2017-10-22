package swa.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinyan on 10/11/17 9:53 PM.
 */
public class BeanParser implements BeanPostProcessor {
    public static final String SERVER_ADDR = "127.0.0.1:8090";
    private static final Logger logger = LoggerFactory.getLogger(BeanParser.class);
    private static final String BEAN_NAME = "BEAN_NAME";
    private static final String METHOD_NAME = "METHOD_NAME";
    private static final String SCHEDULE_CRON = "SCHEDULE_CRON";
//    RpcService rpcService = new RpcServiceImpl();

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (null != method.getAnnotation(JobSchedule.class)) {
                //向服务端发送job名称，method方法
                Map<String, String> map = new HashMap<String, String>();
                map.put(BEAN_NAME, beanName);
                map.put(METHOD_NAME, method.getName());
                map.put(SCHEDULE_CRON, method.getAnnotation(JobSchedule.class).scheduleCron());
//                rpcService.remoteRequest(JSON.toJSONString(map), SERVER_ADDR);
            }
        }
        return bean;
    }
}

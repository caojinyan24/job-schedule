package swa.service.rpc;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.GenericApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by jinyan on 10/12/17 3:30 PM.
 */
public class JobScheduleHandler extends AbstractHandler {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleHandler.class);
    BeanFactory beanFactory = new GenericApplicationContext();

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        //解析请求中的类名和方法名
        String methodName = (String) httpServletRequest.getAttribute("methodName");
        String beanName = (String) httpServletRequest.getAttribute("beanName");
        String param = (String) httpServletRequest.getAttribute("param");
        //解析请求中的请求参数
        //// TODO: 10/16/17 做一个代理方法
        if (beanFactory.containsBean(beanName)) {
            try {
                Method method = beanFactory.getBean(beanName).getClass().getMethod(methodName);
                method.invoke(param);
            } catch (Exception e) {
                logger.error("method invoke error:", e);
            }
        }
        //通过反射调用方法
        request.setHandled(true);
    }
}

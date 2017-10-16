package swa.rpc.service;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jinyan on 10/12/17 3:30 PM.
 */
public class JobRegisterHandler extends AbstractHandler {
    private static final Logger logger = LoggerFactory.getLogger(JobRegisterHandler.class);


    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        // TODO: 10/12/17  做逻辑处理
        //解析请求中的类名和方法名
        //解析请求中的请求参数
        //保存入库
        request.setHandled(true);
    }
}

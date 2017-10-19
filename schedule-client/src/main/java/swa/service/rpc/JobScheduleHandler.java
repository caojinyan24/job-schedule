package swa.service.rpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 任务调度Handler
 * Created by jinyan on 10/12/17 3:30 PM.
 */
public class JobScheduleHandler extends ChannelHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleHandler.class);
    private ApplicationContext applicationContext = new GenericApplicationContext();


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channelRead-begin:{}", msg);
        ByteBuf buf = (ByteBuf) msg;
        try {
            // TODO: 2017/10/19 解析请求信息
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String reqStr=new String(bytes);
            logger.info("channelRead:{}", reqStr);
//            logger.info("instance:{}",);
            if (msg instanceof HttpRequest) {

                HttpRequest req = (HttpRequest) msg;
                //解析请求中的类名和方法名
                HttpHeaders httpHeaders = req.headers();
                logger.info("header:{}",httpHeaders);
                if (!httpHeaders.isEmpty()) {
                    for (Map.Entry entry : httpHeaders) {
                        if ("paramJsonStr".equals(entry.getKey())) {
                            String paramJsonStr = (String) entry.getValue();
                            Map<String, String> jobInfo = JSON.parseObject(paramJsonStr, Map.class);
                            String methodName = jobInfo.get("methodName");
                            String beanName = jobInfo.get("beanName");
                            //解析请求中的请求参数
                            String param = jobInfo.get("param");
                            //// TODO: 10/16/17 做一个代理方法
                            //通过反射调用方法
                            if (applicationContext.containsBean(beanName)) {
                                Method method = applicationContext.getBean(beanName).getClass().getMethod(methodName);
                                method.invoke(param);
                            }
                        }
                    }
                }
            }
            // TODO: 2017/10/19 写入response数据 
            // If keep-alive is off, close the connection once the content is fully written.
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            logger.error("channelRead error:", e);
        } finally {

            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

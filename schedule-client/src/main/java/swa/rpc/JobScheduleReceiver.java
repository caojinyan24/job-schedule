package swa.rpc;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Method;

/**
 * 接收任务调度Handler
 * Created by jinyan on 10/12/17 3:30 PM.
 */
public class JobScheduleReceiver extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleReceiver.class);
    private ApplicationContext applicationContext = new GenericApplicationContext();

    @Override
    public void channelReadComplete(final ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(new GenericFutureListener<ChannelFuture>() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        future.channel().close();
                    }
                });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("recieveMsg:{}", msg);
        String paramStr = (String) msg;
        if (paramStr == null || paramStr == "") {
            return;
        }
        ctx.write(schedule(paramStr));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("catch exception:{}", cause);
        ctx.close();
    }

    private String schedule(String paramStr) {
        try {
            JobContext jobInfo = JSON.parseObject(paramStr, JobContext.class);
            if (jobInfo != null) {
                //// TODO: 10/16/17 做一个代理方法
                //通过反射调用方法
                if (applicationContext.containsBean(jobInfo.getJobName())) {
                    Method method = applicationContext.getBean(jobInfo.getBeanName()).getClass().getMethod(jobInfo.getMethodName());

                    method.invoke(jobInfo.getParam());
                }
            }
        } catch (Exception e) {
            logger.error("schedule invoke error:", e);
        }
        return "success";

    }
}

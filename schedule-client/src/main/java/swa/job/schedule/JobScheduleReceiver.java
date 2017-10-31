package swa.job.schedule;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.job.ApplicationManager;
import swa.job.JobInfo;

import java.lang.reflect.Method;

/**
 * 接收任务调度Handler
 * Created by jinyan on 10/12/17 3:30 PM.
 */
public class JobScheduleReceiver extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleReceiver.class);

    //一种考考虑是在本地维护一个以jobname为key，task实例为value的map队列，其中task中存放了bean实例和method方法
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
            logger.info("schedule:{}", paramStr);
            JobInfo jobInfo = JSON.parseObject(paramStr, JobInfo.class);
            if (jobInfo != null) {
                //// TODO: 10/16/17 做一个代理方法
                //通过反射调用方法
                if (null != ApplicationManager.getBean(jobInfo.getBeanName())) {
                    Method method = ApplicationManager.getBean(jobInfo.getBeanName()).getClass().getMethod(jobInfo.getMethodName(), null);
                    method.invoke(ApplicationManager.getBean(jobInfo.getBeanName()));
                }
            }
        } catch (Exception e) {
            logger.error("schedule invoke error:", e);
        }
        return "success";

    }
}

package swa.job.registry;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.db.service.ScheduleService;
import swa.job.common.ApplicationManager;

/**
 * 接收任务调度Handler
 * Created by jinyan on 10/12/17 3:30 PM.
 */
public class JobInfoReceiver extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JobInfoReceiver.class);
    private final ScheduleService scheduleService = ApplicationManager.getBean(ScheduleService.class);


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
        logger.info("jobInfo received:{}", msg);
        String paramStr = (String) msg;
        if (paramStr == null || paramStr == "") {
            return;
        }
        //保存任务信息到数据库
        scheduleService.saveJobInfo(paramStr);
        ctx.write("success");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("catch exception:{}", cause);
        ctx.close();
    }

}

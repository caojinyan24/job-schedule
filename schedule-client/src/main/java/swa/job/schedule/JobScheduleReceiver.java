package swa.job.schedule;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        final String paramStr = (String) msg;
        logger.info("get job schedule:{}", paramStr);
        if (paramStr == null || paramStr == "") {
            return;
        }
        JobScheduleExecutor.addRegisteredJob(paramStr);
        ctx.write("ADD SUCCESS");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("catch exception:{}", cause);
        ctx.close();
    }


}

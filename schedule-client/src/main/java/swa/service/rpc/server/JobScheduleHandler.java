package swa.service.rpc.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http2.*;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.buffer.Unpooled.unreleasableBuffer;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * 任务调度Handler
 * Created by jinyan on 10/12/17 3:30 PM.
 */
public class JobScheduleHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleHandler.class);
    private ApplicationContext applicationContext = new GenericApplicationContext();
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");
    static final ByteBuf RESPONSE_BYTES = unreleasableBuffer(copiedBuffer("Hello World", CharsetUtil.UTF_8));

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

//            // TODO: 2017/10/19 解析请求信息
////            byte[] bytes = new byte[buf.readableBytes()];
////            buf.readBytes(bytes);
////            String reqStr = new String(bytes);
//            logger.info("channelRead:{}", msg);// TODO: 10/20/17 解析请求参数
////            logger.info("instance:{}",);
//            if (msg instanceof HttpRequest) {
//
//                HttpRequest req = (HttpRequest) msg;
//                //解析请求中的类名和方法名
//                HttpHeaders httpHeaders = req.headers();
//                logger.info("header:{}", httpHeaders);
//                if (!httpHeaders.isEmpty()) {
//                    for (Map.Entry entry : httpHeaders) {
//                        if ("paramJsonStr".equals(entry.getKey())) {
//                            String paramJsonStr = (String) entry.getValue();
//                            Map<String, String> jobInfo = JSON.parseObject(paramJsonStr, Map.class);
//                            String methodName = jobInfo.get("methodName");
//                            String beanName = jobInfo.get("beanName");
//                            //解析请求中的请求参数
//                            String param = jobInfo.get("param");
//                            //// TODO: 10/16/17 做一个代理方法
//                            //通过反射调用方法
//                            if (applicationContext.containsBean(beanName)) {
//                                Method method = applicationContext.getBean(beanName).getClass().getMethod(methodName);
//                                method.invoke(param);
//                            }
//                        }
//                    }
//                }
//            }
//            // TODO: 2017/10/19 写入response数据
//            // If keep-alive is off, close the connection once the content is fully written.
//            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);


            logger.info("readmsg:{}", msg);
            ctx.write(msg);

    }


    private static void sendResponse(ChannelHandlerContext ctx, Http2FrameStream stream, ByteBuf payload) {
        // Send a frame for the response status
        Http2Headers headers = new DefaultHttp2Headers().status(OK.codeAsText());
        ctx.write(new DefaultHttp2HeadersFrame(headers).stream(stream));
        ctx.write(new DefaultHttp2DataFrame(payload, true).stream(stream));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("catch exception:{}", cause);
        ctx.close();
    }

}

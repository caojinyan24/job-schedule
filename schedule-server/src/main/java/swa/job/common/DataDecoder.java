package swa.job.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by jinyan on 10/22/17 12:50 AM.
 */
public class DataDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(DataDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        byte[] decoded = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(decoded);
        list.add(new String(decoded));
        logger.info("decode:list-{}", list);
    }
}

package swa.job;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by jinyan on 10/22/17 12:50 AM.
 */
public class DataDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        byte[] decoded = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(decoded);
        list.add(new String(decoded));
    }
}

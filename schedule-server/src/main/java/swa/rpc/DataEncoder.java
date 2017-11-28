package swa.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jinyan on 10/22/17 12:47 AM.
 */
public class DataEncoder extends MessageToByteEncoder<String> {
    private static final Logger logger = LoggerFactory.getLogger(DataEncoder.class);


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        logger.info("encode:{},{}",s,byteBuf.toString());
        byteBuf.writeBytes(s.getBytes());
    }

}

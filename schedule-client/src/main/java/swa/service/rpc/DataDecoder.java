package swa.service.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
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
        logger.info("decode:{}", byteBuf.array());
        if (byteBuf.readableBytes() < 5) {
            return;
        }
        byteBuf.markReaderIndex();
        // Check the magic number.
        int readByte = byteBuf.readUnsignedByte();

        if (readByte != 'F') {
            byteBuf.resetReaderIndex();
            throw new CorruptedFrameException("Invalid magic number: " + readByte);
        }

        // Wait until the whole data is available.
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }

        // Convert the received data into a new BigInteger.
        byte[] decoded = new byte[dataLength];
        byteBuf.readBytes(decoded);

        list.add(new String(decoded));

        logger.info("decode:list-{}", list);
    }
}

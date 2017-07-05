package yangsh.test.netty.coder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 16进制数据解码器
 * @Author 许亮
 * @Create 2016-7-18 17:32:04
 */
public class HexDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		byte[] bytes = new byte[in.readableBytes()];
		in.readBytes(bytes);
		String message=new String(bytes,"utf-8");
//		 StringUtil.ByteArrayToHexadecimal(bytes, true);
		out.add(message);
	}
}

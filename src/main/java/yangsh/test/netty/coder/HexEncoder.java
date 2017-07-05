package yangsh.test.netty.coder;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import yangsh.test.netty.Util;

/**
 * 形如“58 0A 00 00 38 FF”的16进制数据编码器
 * @Author 许亮
 * @Create 2016-7-20 17:34:18
 */
public class HexEncoder extends MessageToByteEncoder<String> {
	/**
	 * 将16进制数据转换成16进制字节数组
	 * @param source 形如“58 0A 00 00 38 FF”的16进制数据
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private byte[] prepareHexBytes(String source) throws UnsupportedEncodingException {
		if (Util.checkNotNull(source)) {
			return null;
		}
		
//		String[] hexArray = source.split(" ");
//		byte[] bytes = new byte[hexArray.length];
//		
//		for (int i = 0; i < hexArray.length; i++) {
//			bytes[i] = (byte) Integer.parseInt(hexArray[i], 16);
//		}
		byte[]bytes=source.getBytes("utf-8");
		return bytes;
	}

	/**
	 * 消息转字节编码处理
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, String message, ByteBuf out) throws Exception {
		byte[] outBytesData = prepareHexBytes(message);
		if (outBytesData == null) {
			return;
		}
		
		out.writeBytes(outBytesData);
	}
}

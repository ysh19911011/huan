package yangsh.test.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端消息处理器
 * @Author 许亮
 * @Create 2016-7-13 12:44:01
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {
	private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("client channelActive..");
		}
		super.channelActive(ctx);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("客户端<channelRead0>收到消息：" + msg);
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx){
//		System.out.println("客户端消息读取完毕");
		ctx.fireChannelReadComplete();
	}
}

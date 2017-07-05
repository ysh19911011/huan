package yangsh.test.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import yangsh.test.netty.handler.ServerHandler;

public class NioSocketResponseImpl implements NioSocketServerResponse {

	public void handle(String socketClientMessage) {
		System.out.println(socketClientMessage);
	}

	public String handleAndResponse(String socketClientMessage) {
		System.out.println(socketClientMessage);
		return "收到";
	}

	public void setEquipmentOnlineStatus(String eqpSn, String channelId) {
		
	}

	public ChannelFuture sendMessage(String channelId,String message) {
		return null;
//		ChannelHandlerContext ctx=ServerHandler.getServerChannel(channelId);
//		return ctx.writeAndFlush(message);
	}

}

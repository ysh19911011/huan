package yangsh.test.netty.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import yangsh.test.netty.NioSocketServerResponse;
import yangsh.test.netty.Util;

/**
 * 专用16进制数据处理器，其中注册了decoder、handler、encoder三个处理器，并且有先后顺序。<br>
 * 服务端或客户端收到消息的处理顺序为：decoder → handler → encoder<br>
 * @Author 许亮
 * @Create 2016-7-12 16:24:38
 */
public class ProtocolHexInitalizer extends ChannelInitializer<SocketChannel> {
//	private NioSocketServerResponse nioChannelResponse;
	
	public ProtocolHexInitalizer() {
//		this.nioChannelResponse = nioChannelResponse;
	}

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline channelPipeline = socketChannel.pipeline();
		channelPipeline.addLast(new StringDecoder());
		channelPipeline.addLast(new StringEncoder());
		channelPipeline.addLast("handler", new ServerHandler()
		{
			@Override
			protected void channelProcessor(ChannelHandlerContext ctx, String message) {
				//String response = nioChannelResponse.handleAndResponse(message); // 上层业务处理
				//ctx.channel().writeAndFlush(response);
//				nioChannelResponse.handle(message);
				System.out.println(message);
				if(Util.checkNotNull(message)&&!message.isEmpty()){
//					ctx.writeAndFlush("connected");
					ServerHandler.addId2Channel(message, ctx.channel().id().asLongText());
//					System.out.println("id2channel 放入。。。。。"+ctx.channel().id().asLongText()+"----------"+message);
				}
			}
		}
		);
	}
}

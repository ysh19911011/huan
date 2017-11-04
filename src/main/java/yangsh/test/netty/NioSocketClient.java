package yangsh.test.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import yangsh.test.netty.handler.ClientHandler;

/**
 * 非阻塞式Socket客户端
 * @Author 许亮
 * @Create 2016-7-12 17:14:44
 */
public class NioSocketClient {
	private EventLoopGroup workerGroup;
	private ChannelFuture channelFuture;

	public NioSocketClient(InetSocketAddress socketAddress) throws InterruptedException {
		connect(socketAddress);
	}
	private void connect(InetSocketAddress socketAddress) throws InterruptedException {
		// 客户端NIO线程组
		workerGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(workerGroup)
				 .channel(NioSocketChannel.class)
				 .option(ChannelOption.TCP_NODELAY, true)
				 .option(ChannelOption.SO_KEEPALIVE, true)
				 .handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline()
												.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4))
												.addLast("frameEncoder", new LengthFieldPrepender(4))
												.addLast("decoder",new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())))
												.addLast("encoder",new ObjectEncoder())
												.addLast("handler",new ClientHandler());
					}
				});
		
		// 发起异步连接操作
		channelFuture = bootstrap.connect(socketAddress).sync();
		send("5943a56ab34a0b011ccf7bb9");
//		System.out.println("conn..");
	}
	
	public void disConnect() {
		try {
			// 等待客户端链路关闭
			channelFuture.channel().closeFuture().sync();
			
			if (workerGroup != null) {
				// 释放NIO线程组
				workerGroup.shutdownGracefully();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发消息
	 * @param message
	 */
	public void send(String message) {
		channelFuture.channel().writeAndFlush(message).addListener(new ChannelFutureListener(){
				public void operationComplete(ChannelFuture future){
					if(future.isSuccess()){
						System.out.println("消息发送成功");
					}else{
						System.out.println("failed to send");
						future.channel().close();
					}
				}
			});
	}
}

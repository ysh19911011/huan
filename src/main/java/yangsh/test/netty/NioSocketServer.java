package yangsh.test.netty;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import yangsh.test.netty.handler.ServerHandler;

/**
 * 非阻塞式Socket服务端，默认绑定监听端口：16688
 * @Author 许亮
 * @Create 2016-7-12 15:27:02
 */
public class NioSocketServer {
private static final Logger logger = LoggerFactory.getLogger(NioSocketServer.class);
	
	/**
	 * Socket服务监听IP地址及端口
	 */
	private InetSocketAddress tcpPort;
	/**
	 * Socket服务主线程数量
	 */
	private int bossThreadSize = 0;
	/**
	 * Socket服务主线程池
	 */
	private EventLoopGroup bossGroup;
	/**
	 * Socket服务工作线程数量
	 */
	private int workerThreadSize = 0;
	/**
	 * Socket服务工作线程池
	 */
	private EventLoopGroup workerGroup;
	/**
	 * Socket响应
	 */
	private ChannelFuture channelFuture;
	/**
	 * 构造器函数
	 * @param listenPort Socket服务监听端口，默认端口：16688
	 */
	public NioSocketServer(Integer listenPort) {
		if (listenPort == null) {
			listenPort = 16688;
		}
		if (listenPort <= 0) {
			listenPort = 16688;
		}
		
		tcpPort = new InetSocketAddress(listenPort);
	}
	/**
	 * 初始化时运行
	 */
	@PostConstruct
	public void start() {
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(getBossGroup(), getWorkerGroup())
						   .channel(NioServerSocketChannel.class)
						   .childHandler(new ChannelInitializer<SocketChannel>(){
							   @Override
								protected void initChannel(SocketChannel socketChannel) throws Exception {
									ChannelPipeline channelPipeline = socketChannel.pipeline();
									channelPipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
									channelPipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
									channelPipeline.addLast("decoder",new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
									channelPipeline.addLast("encoder",new ObjectEncoder());
									channelPipeline.addLast("handler", new ServerHandler(){
										@Override
										protected void channelProcessor(ChannelHandlerContext ctx, Object message) {
											if(Util.checkNotNull(message)&&!message.toString().isEmpty()){
												ServerHandler.addId2Channel(message.toString(), ctx.channel().id().asLongText());
												System.out.println(ServerHandler.id2ChannelMap);
											}
										}
									});
								}
						   })
						   .option(ChannelOption.SO_BACKLOG, 512)
						   .option(ChannelOption.SO_KEEPALIVE, true);
						   
			// 绑定端口
			channelFuture = serverBootstrap.bind(tcpPort).sync();
		} catch (InterruptedException e) {
			System.exit(-1);
		}
		logger.info("Starting Tcp Socket Server at " + tcpPort);
	}
	/**
	 * 销毁时运行
	 * @throws InterruptedException
	 */
	@PreDestroy
	public void stop() throws InterruptedException {
		channelFuture.channel().closeFuture().sync();
		
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
	}
	/**
	 * 获取Socket服务主线程池
	 * @return
	 */
	private EventLoopGroup getBossGroup() {
		if (bossThreadSize > 0) {
			bossGroup = new NioEventLoopGroup(bossThreadSize);
		} else {
			bossGroup = new NioEventLoopGroup();
		}
		
		return bossGroup;
	}
	/**
	 * 获取Socket服务工作线程池
	 * @return
	 */
	private EventLoopGroup getWorkerGroup() {
		if (workerThreadSize > 0) {
			workerGroup = new NioEventLoopGroup(workerThreadSize);
		} else {
			workerGroup = new NioEventLoopGroup();
		}
		
		return workerGroup;
	}
	/**
	 * 设置Socket服务主线程数量
	 * @param bossThreadSize
	 */
	public void setBossThreadSize(int bossThreadSize) {
		this.bossThreadSize = bossThreadSize;
	}

	/**
	 * 设置Socket服务工作线程数量
	 * @param workerThreadSize
	 */
	public void setWorkerThreadSize(int workerThreadSize) {
		this.workerThreadSize = workerThreadSize;
	}
 }

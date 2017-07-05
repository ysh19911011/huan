package yangsh.test.junitTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import org.junit.Test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import yangsh.test.netty.NioSocketClient;
import yangsh.test.netty.coder.HexDecoder;
import yangsh.test.netty.coder.HexEncoder;
import yangsh.test.netty.handler.ClientHandler;

/**
 * 
 * @Author 许亮
 * @Create 2016-7-12 17:24:39
 */
public class TestConnectSocketServer {
	private NioSocketClient socketClient;
	
	@Test
	public void connect() throws InterruptedException {
		InetSocketAddress socketAddress = new InetSocketAddress("localhost", 16688);
		socketClient = new NioSocketClient(socketAddress);
		socketClient.send("i am client.");
		//socketClient.disConnect();
	}
	
	@Test
	public void typeMatch() {
		System.out.println(Double.class == Double.class);
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
            			@Override
            			protected void initChannel(SocketChannel socketChannel) throws Exception {
            				socketChannel.pipeline().addLast(new HexDecoder(), new HexEncoder(), new ClientHandler());
            				//socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(), new BaseClientHandler());
            			}
            		});

            // 连接服务端
            Channel ch = b.connect("192.168.1.242", 16688).sync().channel();

            // 控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    System.in));
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    continue;
                }
                /*
                 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾 之所以用\r\n结尾 是因为我们在handler中添加了
                 * DelimiterBasedFrameDecoder 帧解码。
                 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
                 */
                ch.writeAndFlush(line);
            }
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
	}
}

package yangsh.test.netty.nettyTest;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import yangsh.test.netty.NioSocketResponseImpl;
import yangsh.test.netty.NioSocketServer;
import yangsh.test.netty.NioSocketServerResponse;
import yangsh.test.netty.handler.ServerHandler;
@Component
public class nettyServerTest {
	@Autowired
	private NioSocketServer nettyServer;
	public static void main(String[] args) {
		NioSocketServer nioServer=new NioSocketServer(null);
//		NioSocketServerResponse nssr=new NioSocketResponseImpl();
//		nioServer.setNioChannelResponse(nssr);
		nioServer.start();
//		for(;;){
//			Scanner scan=new Scanner(System.in);
//			String input=scan.nextLine();
////			String channelId=ServerHandler.id2Channel();
////			System.out.println("channelId:-----------------"+channelId);
////			ServerHandler.getServerChannel(channelId).writeAndFlush(input);
//			ServerHandler.sendMessage("power",input).addListener(new ChannelFutureListener(){
//				public void operationComplete(ChannelFuture future){
//					if(future.isSuccess()){
//						System.out.println("消息发送成功");
//					}else{
//						System.out.println("failed to send");
//						future.channel().close();
//					}
//				}
//			});
//		}
		
		
		
	}
}

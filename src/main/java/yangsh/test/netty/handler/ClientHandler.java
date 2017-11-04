package yangsh.test.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端消息处理器
 * @Author 许亮
 * @Create 2016-7-13 12:44:01
 */
public class ClientHandler extends SimpleChannelInboundHandler {
	private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("client channelActive..");
		}
		super.channelActive(ctx);
	}
	private static String temp="";
	//当发送消息编解码为object时,走的是这个方法  而不是channelRead0
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg){
		System.out.println(1);
		
		System.out.println(msg.toString());
	}
//	@Override
//	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//		System.out.println("客户端<channelRead0>收到消息：" + msg);
//		System.out.println("消息长度:"+msg.length());
////		if(msg.endsWith("netty$$end")){
////			temp+=msg;
////			System.out.println("客户端<channelRead0>收到消息：" + temp);
////			System.out.println("消息长度:"+temp.length());
////			temp="";
////		}else{
////			temp+=msg;
////		}
//	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx){
		System.out.println("客户端消息读取完毕");
		ctx.fireChannelReadComplete();
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(2);
		System.out.println(msg.toString());
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
		ctx.close();
	}
}

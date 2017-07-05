package yangsh.test1;

import java.io.File;
import java.io.RandomAccessFile;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by Lison-Liou on 5/17/2016.
 */
public class EchoServerHandler extends SimpleChannelInboundHandler<Object> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 文件存储路径
     */
    private String FILE_SAVE_PATH = "D:";

    /**
     * byte[] seek step size
     */
    private int DATA_LENGTH = 1024;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel incoming = ctx.channel();

        if (msg instanceof EchoFile) {
            EchoFile ef = (EchoFile) msg;
            int SumCountPackage = ef.getSumCountPackage();
            int countPackage = ef.getCountPackage();
            byte[] bytes = ef.getBytes();
            String file_name = ef.getFile_name();//文件名

            String path = FILE_SAVE_PATH + File.separator + file_name;
            File file = new File(path);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(countPackage * DATA_LENGTH - DATA_LENGTH);
            randomAccessFile.write(bytes);

            System.out.println("SYSTEM TOTAL PACKAGE COUNT：" + ef.getSumCountPackage());
            System.out.println("SYSTEM NOT IS THE " + countPackage + "th PACKAGE");
            System.out.println("SYSTEM PACKAGE COUNT: " + bytes.length);

            countPackage = countPackage + 1;

            if (countPackage <= SumCountPackage) {
                ef.setCountPackage(countPackage);
                ctx.writeAndFlush(ef);
                randomAccessFile.close();

                ctx.writeAndFlush("SYSTEM " + countPackage + " UPLOADED");
            } else {
                randomAccessFile.close();
                ctx.close();
                ctx.writeAndFlush("SYSTEM " + ef.getFile_name() + " UPLOAD FINISHED");
            }
        } else if (msg instanceof EchoMessage) {
            EchoMessage em = (EchoMessage) msg;
            System.out.println("RECEIVED: " + ctx.channel().remoteAddress() + " " + new String(em.getBytes(), CharsetUtil.UTF_8));

            for (Channel channel : channels) {
                String m = new String(em.getBytes(), CharsetUtil.UTF_8);
                if (channel == incoming)
                    incoming.writeAndFlush("YOU " + m);
                else
                    channel.writeAndFlush(incoming.remoteAddress() + " " + m);
            }
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        System.out.println("SYSTEM CHANNEL REMOVED: " + incoming.remoteAddress());

        for (Channel channel : channels) {
            if (channel == incoming)
                incoming.writeAndFlush("YOU OFFLINE");
            else
                channel.writeAndFlush(incoming.remoteAddress() + " OFFLINE");
        }

        channels.remove(incoming);
        System.out.println("SYSTEM CHANNEL SIZE: " + channels.size());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        System.out.println("SYSTEM CHANNEL ADDED: " + incoming.remoteAddress());

        channels.add(incoming);
        System.out.println("SYSTEM CHANNEL SIZE: " + channels.size());
        for (Channel channel : channels) {
            if (channel == incoming)
                incoming.writeAndFlush("YOU ONLINE").addListener(new ChannelFutureListener(){
    				public void operationComplete(ChannelFuture future){
    					if(future.isSuccess()){
    						System.out.println("消息发送成功");
    					}else{
    						System.out.println("failed to send");
    						future.channel().close();
    					}
    				}
    			});
            else
                channel.writeAndFlush(incoming.remoteAddress() + " ONLINE");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        ctx.writeAndFlush(incoming.remoteAddress() + " JOINED");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        ctx.writeAndFlush(incoming.remoteAddress() + " QUIT");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.writeAndFlush("[SYSTEM ERROR] - " + cause.getMessage());
        ctx.close();
    }
}

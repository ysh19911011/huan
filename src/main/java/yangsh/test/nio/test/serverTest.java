package yangsh.test.nio.test;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import yangsh.test.nio.service.NioService3;

public class serverTest {
	public static void main(String[] args) throws IOException {
		NioService3 nioService=new NioService3();
		ServerSocketChannel channel=nioService.initServer(8585);
		nioService.listen();
		while(true){
		}
	}
}

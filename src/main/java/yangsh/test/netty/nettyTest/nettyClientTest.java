package yangsh.test.netty.nettyTest;

import java.net.InetSocketAddress;
import java.util.Scanner;

import com.alibaba.fastjson.JSONObject;

import yangsh.test.netty.NioSocketClient;

public class nettyClientTest {
	public static void main(String[] args) {
		try {
			NioSocketClient nioClient=new NioSocketClient(new InetSocketAddress("192.168.0.119",16688));
//			Scanner scan=new Scanner(System.in);
//			String input;
//			for(;;){
//				input=scan.nextLine();
//				nioClient.send(input);
//			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package yangsh.test.netty.nettyTest;

import yangsh.test.netty.handler.ServerHandler;

public class baseTest {
	public static void main(String[] args) {
		for(;;){
			ServerHandler.sendMessage("19911011", "look at me!!!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

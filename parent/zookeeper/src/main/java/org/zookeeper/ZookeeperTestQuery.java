package org.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.VoidCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperTestQuery implements Watcher {

	private static final String zooipaddress = "192.168.186.138:2181";
	private static final int timeout = 5000;
	private static ZooKeeper zk;

	public static void main(String[] args) throws IOException, InterruptedException {
		zk = new ZooKeeper(zooipaddress, timeout, new ZookeeperTestQuery());
		System.out.println(zk.getState());
		Thread.sleep(500000);
	}

	public void process(WatchedEvent arg0) {
		System.out.println(arg0);
		try {
			//同步获取
/*			byte[]  datas=  zk.getData("/snail", true, null);
			System.out.println(new String(datas));
*/			
			//2异步获取
			zk.getData("/snail", true, new AsynMyDataCallback(), "上线问");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	class AsynMyDataCallback  implements DataCallback{

		public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
			System.out.println(rc);
			System.out.println(path);
			System.out.println(ctx);
			System.out.println(new String(data));
			System.out.println(stat);
		}
		
		
	}
	

}

package org.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperTestModify implements Watcher {

	private static final String zooipaddress = "192.168.186.138:2181";
	private static final int timeout = 5000;
	private static ZooKeeper zk;

	public static void main(String[] args) throws IOException, InterruptedException {
		zk = new ZooKeeper(zooipaddress, timeout, new ZookeeperTestModify());
		System.out.println(zk.getState());
		Thread.sleep(500000);
	}

	public void process(WatchedEvent arg0) {
		System.out.println(arg0);
		try {
			// 1同步修改节点
			//-1表示忽略版本号
			//Stat path=zk.setData("/snail", "888".getBytes(),-1);
			//System.out.println(path);
			
			 //2异步修改  没有返回值
			zk.setData("/snail", "888999".getBytes(),-1, new myAsycStatCallback(), "上下文");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 异步修改节点回调
	 * @author snail
	 *
	 */
	class myAsycStatCallback implements StatCallback {

		public void processResult(int rc, String path, Object ctx, Stat stat) {
				System.out.println(rc);
				System.out.println(path);
				System.out.println(ctx);
				System.out.println(stat);
		}

	}

}

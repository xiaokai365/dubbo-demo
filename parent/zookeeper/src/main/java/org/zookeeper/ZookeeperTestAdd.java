package org.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperTestAdd implements Watcher {

	private static final String zooipaddress = "192.168.186.138:2181";
	private static final int timeout = 5000;
	private static ZooKeeper zk;

	public static void main(String[] args) throws IOException, InterruptedException {
		zk = new ZooKeeper(zooipaddress, timeout, new ZookeeperTestAdd());
		System.out.println(zk.getState());
		Thread.sleep(500000);
	}

	public void process(WatchedEvent arg0) {
		System.out.println(arg0);
		try {
			// 1同步创建节点
			//String path = zk.create("/snail", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			//System.out.println(path);
			// 2异步创建节点  异步创建没有返回值
			zk.create("/demo001", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new myAsycCallback(), "上下文");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 异步创建节点回调
	 * @author snail
	 *
	 */
	class myAsycCallback implements StringCallback {

		/**
		 * ctx上下文，为zk.create("/demo001", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new myAsycCallback(), null);
		 * 中的最后一个参数
		 */
		public void processResult(int rc, String path, Object ctx, String name) {
				System.out.println(rc);
				System.out.println(path);
				System.out.println(ctx);
				System.out.println(name);
				
		}

	}

}

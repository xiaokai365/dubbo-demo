package org.zookeeper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperTestQueryChilds implements Watcher {

	private static final String zooipaddress = "192.168.186.138:2181";
	private static final int timeout = 5000;
	private static ZooKeeper zk;

	public static void main(String[] args) throws IOException, InterruptedException {
		zk = new ZooKeeper(zooipaddress, timeout, new ZookeeperTestQueryChilds());
		System.out.println(zk.getState());
		Thread.sleep(500000);
	}

	public void process(WatchedEvent arg0) {
		System.out.println(arg0);
		try {
			// 1获取/snail下的子节点node
		/*	List<String> childs = zk.getChildren("/snail", true);
			for (String data : childs) {
				System.out.println(data);
			}*/
			//2在/snail的子节点上创建监听事件
			/**
			 * 该事件只能监听一次
			 * 要重复监听只能触发后再次注册时间
			 */
			List<String> childs2 =zk.getChildren("/snail", new MyWatcher());
			for (String data : childs2) {
				System.out.println(data);
			}
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	class MyWatcher implements Watcher{

		public void process(WatchedEvent event) {
			System.out.println("===="+event);
		}
		
		
	}
}

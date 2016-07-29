package org.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperTestHeader implements Watcher {

	private static final String zooipaddress = "192.168.186.138:2181";
	private static final int timeout = 5000;
	private static ZooKeeper zk;

	public static void main(String[] args) throws IOException, InterruptedException {
		zk = new ZooKeeper(zooipaddress, timeout, new ZookeeperTestHeader());
		System.out.println(zk.getState());
		Thread.sleep(500000);
	}

	
	private void dosomething(){
		
		try {
			Stat  stat=zk.setData("/snail", "333".getBytes(), -1);
			System.out.println(stat);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void process(WatchedEvent event) {
		if(event.getState()==KeeperState.SyncConnected){
				if(event.getType()==EventType.None && null==event.getPath()){
					//1 do some thing   第一次连接正常后执行
					System.out.println("第一次调用");
					dosomething();
				}else{
					if(event.getType()==EventType.NodeChildrenChanged){//子节点个数发生变化
						
					}else if(event.getType()==EventType.NodeDataChanged){//节点数据发生改变
						System.out.println(event.getPath()+" updated");
						try {
							System.out.println(zk.exists(event.getPath(), true));
						} catch (KeeperException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else if(event.getType()==EventType.NodeCreated){//节点创建
						
					}else if(event.getType()==EventType.NodeDeleted){//节点删除
						
					}
					
				}
			
		}
		
	}

	
}

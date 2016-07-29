package org.zookeeper.zkclient.event;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class SubscribleChildNodesChanges {
	
	
	private  static class myIZkChildListener implements IZkChildListener{

		public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
			System.out.println(parentPath);
			System.out.println(currentChilds.toString());
		}
		
	}
	
	public static void main(String[] args) {
		ZkClient  zkclient=new ZkClient("192.168.186.138:2181", 5000, 5000, new SerializableSerializer());
		System.out.println("zk连接正常");
		//监听/snail下子节点创建删除事件   以及本身/snail路径的的创建和删除
		List<String> datas= zkclient.subscribeChildChanges("/snail", new myIZkChildListener());
		
		try {
			Thread.sleep(555555);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
}

package org.zookeeper.zkclient.event;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.zookeeper.pojo.User;

public class SubscribleDataChanges {

	private static class myIZkDataListener  implements IZkDataListener{

		public void handleDataChange(String dataPath, Object data) throws Exception {
			//这里面运用SerializableSerializer这个序列化不会收到监听结果，因为用zkcli。sh客户端修改是不支持java对象序列化的
			//读写是2套逻辑
			//可以通过java客户端进行修改操作
			//zkclient.writeData("/snail", new User(22,"撒谎的"));
			System.out.println(dataPath);
			System.out.println(data.toString());
		}

		public void handleDataDeleted(String dataPath) throws Exception {
			System.out.println(dataPath);
		}
		
		
	}
	
	public static void main(String[] args) {
		ZkClient  zkclient=new ZkClient("192.168.186.138:2181", 5000, 5000, new SerializableSerializer());
		System.out.println("zk连接正常");
		
		zkclient.subscribeDataChanges("/snail", new myIZkDataListener());
		
		try {
			Thread.sleep(500000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

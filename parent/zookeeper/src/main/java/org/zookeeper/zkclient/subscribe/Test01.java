package org.zookeeper.zkclient.subscribe;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class Test01 {

	// zookeeper服务器的地址
	private static final String ZOOKEEPER_SERVER = "192.168.186.138:2181";

	public static void main(String[] args) {
		ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000, new SerializableSerializer());
		client.subscribeDataChanges("/root", new IZkDataListener() {
			
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println(dataPath);
			}
			
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println(data+"========"+dataPath);
			}
		});
		
		System.out.println();
		try {
			Thread.sleep(50000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

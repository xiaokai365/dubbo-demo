package org.zookeeper.zkclient.subscribe;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class Test02 {
	
	// zookeeper服务器的地址
	private static final String ZOOKEEPER_SERVER = "192.168.186.138:2181";

	public static void main(String[] args) {
		ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000, new SerializableSerializer());
		client.writeData("/root", "5555");
	}

}

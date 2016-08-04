package org.zookeeper.zkclient.lock;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class Test000 {

	public static void main(String[] args) {
		for(long i=0;i<Long.MAX_VALUE;i++){
			new  Runnable() {
				public void run() {
					ZkClient client = new ZkClient("192.168.186.138:2181", 5000, 5000, new SerializableSerializer());
					String path=client.createEphemeralSequential("/root/tmp", "123");
					client.close();
					System.out.println(path);
				}
			}.run();
			
		}
	}

}

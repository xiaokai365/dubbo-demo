package org.zookeeper.zkclient.masterslave;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class LeaderSelectorZkClient {
	//启动的服务个数
	private static final int CLIENT_QTY = 10;
	//zookeeper服务器的地址
	private static final String ZOOKEEPER_SERVER = "192.168.186.138:2181";
	
	
	public static void main(String[] args) throws Exception{
		List<ZkClient> clients = new ArrayList<ZkClient>();
		List<WorkServer> workServers = new ArrayList<WorkServer>();
		try{
			for ( int i = 0; i < CLIENT_QTY; ++i ){
				ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000, new SerializableSerializer());
				clients.add(client);
				RunningData runningData = new RunningData();
				runningData.setCid(Long.valueOf(i));
				runningData.setName("Client #" + i);
				WorkServer workServer = new WorkServer(runningData);
				workServer.setZkClient(client);
				workServers.add(workServer);
				workServer.start();
			}
			System.out.println("敲回车键退出！\n");
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		}finally{
			System.out.println("Shutting down...");
			for ( WorkServer workServer : workServers ){
				try {
					workServer.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for ( ZkClient client : clients ){
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
}

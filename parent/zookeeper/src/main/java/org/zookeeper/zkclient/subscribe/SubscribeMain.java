package org.zookeeper.zkclient.subscribe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class SubscribeMain {
	//启动的服务个数
	private static final int CLIENT_QTY = 3;
	//zookeeper服务器的地址
	private static final String ZOOKEEPER_SERVER = "192.168.186.138:2181";
	//外部输入命令
	private static String cmdpath="/cmdpath";
	
	private static String serverpath="/server";

	
	
	public static void main(String[] args) throws Exception{
		List<ZkClient> clients = new ArrayList<ZkClient>();
		List<WorkServer> workServers = new ArrayList<WorkServer>();
		try{
			for ( int i = 0; i < CLIENT_QTY; ++i ){
				ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 5000, 5000, new SerializableSerializer());
				Serverconfig  serverconfig=new Serverconfig();
				serverconfig.setIpaddress("192.168.11.111");
				serverconfig.setUsername("root");
				serverconfig.setUserpasss("123456");
				client.createEphemeral(serverpath+"/demo00"+i,serverconfig);//创建临时节点
				WorkServer  server= new WorkServer(serverpath, client,serverconfig,cmdpath);
				server.start();
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

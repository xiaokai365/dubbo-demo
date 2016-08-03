package org.zookeeper.zkclient.subscribe;

import java.util.List;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class WorkServer {

	private String cmdpath;

	private String serverpath;

	private Serverconfig serverconfig;

	private IZkDataListener dataListener;

	private ZkClient zkClient;

	public WorkServer(String serverpath, ZkClient zkClient, Serverconfig serverconfig, String cmdpath) {
		this.serverconfig = serverconfig;
		this.serverpath = serverpath;
		this.cmdpath = cmdpath;
		this.zkClient = zkClient;
		this.dataListener = new IZkDataListener() {

			public void handleDataChange(String dataPath, Object data) throws Exception {
				// list all server
				//读取传入的参数data写入到server下的子节点上AUTO
				System.out.println(data+"===="+dataPath);
				modify();
			}

			public void handleDataDeleted(String dataPath) throws Exception {
						System.out.println("1111");
			}

		};

	}

	private void modify() {
			//list all server
		List<String>  childsurls=  zkClient.getChildren(serverpath);
		for(String child:childsurls){
			System.out.println("childpath"+child);
			Serverconfig  data=  zkClient.readData(child);
			System.out.println(data);
		}
	}

	public void stop() {
		System.out.println(serverconfig.getIpaddress() + " server is stop....");
		zkClient.unsubscribeDataChanges(cmdpath, dataListener);
	}

	public void start() {
		System.out.println(serverconfig.getIpaddress() + " server is start");
		//监听/cmdpath下面内容的变化
		zkClient.subscribeDataChanges(cmdpath, dataListener);
	}

}

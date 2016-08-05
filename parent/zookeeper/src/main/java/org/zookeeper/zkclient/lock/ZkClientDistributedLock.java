package org.zookeeper.zkclient.lock;

import java.util.Collections;
import java.util.List;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event;

public class ZkClientDistributedLock{

		private int threadid=0;
		private  String nodepath;
		private ZkClient  zkClient;
		private String waitPath;
		
		//zookeeper服务器
		private final String SERVER="192.168.186.138:2181";
		
		private  final String LOCKPATH="/lock";
		
		private  final String TMP="/tmp";
		
		
		public ZkClientDistributedLock(int threadid){
			this.threadid=threadid;
		}

		//开始运行
		public void center(){	
			createpath();
			if(getLock()){
				dosomething();
			}
		}
		
		protected void createpath() {
			zkClient=new ZkClient(SERVER, 5000, 5000, new SerializableSerializer());
			try{
				 nodepath=  zkClient.createEphemeralSequential(LOCKPATH+TMP,"线程【"+threadid+"】创建");
			}catch(ZkNoNodeException e){
				//创建父节点
				zkClient.createPersistent(LOCKPATH);
				createpath();
			}
		}

		private boolean getLock() {
			List<String> childnodes=  zkClient.getChildren(LOCKPATH);
			Collections.sort(childnodes);
			int index=childnodes.indexOf(nodepath.replaceAll(LOCKPATH+"/", ""));
			
			switch (index) {
				case 0:
					System.out.println(threadid+"所有子节点中我最大");
					return true;
				case -1:
					System.out.println(nodepath+"节点已经被删除");
					return false;
				default:
					waitPath=LOCKPATH+"/"+childnodes.get(index-1);
					System.out.println(nodepath+"不是最小的那个，监听上一个的删除事件"+waitPath);
					zkClient.subscribeDataChanges(waitPath, new IZkDataListener() {//订阅前面一个的节点删除事件
						public void handleDataDeleted(String dataPath) throws Exception {
								System.out.println("排在我前面的"+nodepath+"已经被删除了---------"+dataPath+"我要去工作了");
								if(getLock()){
									dosomething();
								}
						}
						
						public void handleDataChange(String dataPath, Object data) throws Exception {
							
						}
					});
					
					Object obj=  zkClient.readData(waitPath);
					return false;
				}
			
		}

		private void dosomething() {
				System.out.println("当前线程"+threadid+"获得锁资源进行操作"+nodepath);
				try {
					Thread.sleep(1000);//模拟进行操作花费时间
					System.out.println("删除节点"+nodepath);
					zkClient.delete(nodepath);
					zkClient.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		
}

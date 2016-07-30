package org.zookeeper.zkclient.masterslave;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorkServer {

	// 客户端状态
	private volatile boolean running = false;
	private ZkClient zkClient;
	// zk主节点路径
	public static final String MASTER_PATH = "/master";
	//// 监听(用于监听主节点删除事件)
	private IZkDataListener dataListener;
	// 服务器基本信息
	private RunningData serverData;
	// 主节点基本信息
	private RunningData masterData;
	// 调度器
	private ScheduledExecutorService delayExector = Executors.newScheduledThreadPool(1);
	// 延迟时间5s
	private int delayTime = 5;

	public WorkServer(RunningData runningData) {

		this.serverData = runningData;
		this.dataListener = new IZkDataListener() {

			public void handleDataChange(String dataPath, Object data) throws Exception {

			}

			public void handleDataDeleted(String dataPath) throws Exception {
				if (masterData != null && masterData.getName().equals(serverData.getName())) {
					//若之前master为本机,则立即抢主,否则延迟5秒抢主(防止小故障引起的抢主可能导致的网络数据风暴)
					takeMaster();//是自己则抢先注册
				} else {
					delayExector.schedule(new Runnable() {
						public void run() {
							takeMaster();
						}

					}, delayTime, TimeUnit.SECONDS);
				}
			}

		};

	}

	public void start() throws Exception {
		if (running) {
			throw new Exception("server has startup....");
		}
		running = true;
		zkClient.subscribeDataChanges(MASTER_PATH,dataListener);
		takeMaster();
	}

	public void stop() throws Exception {
		if (!running) {
			throw new Exception("server has stopped.....");
		}
		running = false;
		delayExector.shutdown();
		zkClient.unsubscribeDataChanges(MASTER_PATH, dataListener);
		releaseMaster();
	}

	private void releaseMaster() {
		if (checkMaster()) {
			zkClient.delete(MASTER_PATH);
		}
	}

	public void setZkClient(ZkClient zkClient) {
		this.zkClient = zkClient;
	}

	public ZkClient getZkClient() {
		return zkClient;
	}

	private boolean checkMaster() {
		try {
			RunningData runningData = zkClient.readData(MASTER_PATH);
			masterData = runningData;
			if (masterData.getName().equals(serverData.getName())) {
				return true;
			}
			return false;
		} catch (ZkNoNodeException e) {// 节点不存在
			return false;
		} catch (ZkInterruptedException e) {// 网络中断
			return checkMaster();
		} catch (Exception e) {// 其它
			return false;
		}
	}

	private void takeMaster() {
		if (!running)
			return;
		try {
			//创建临时节点CreateMode.EPHEMERAL
			zkClient.create(MASTER_PATH, serverData, CreateMode.EPHEMERAL);
			masterData = serverData;
			System.out.println(serverData.getName() + " is master");
			//测试每隔5秒释放master权限
			delayExector.schedule(new Runnable() {
				public void run() {
					if (checkMaster()) {
						releaseMaster();
					}
				}
			}, 5, TimeUnit.SECONDS);
		} catch (ZkNodeExistsException e) {
			RunningData runningData = zkClient.readData(MASTER_PATH, true);
			if (runningData == null) {
				takeMaster();
			} else {
				masterData = runningData;
			}

		} catch (Exception e) {

		}

	}

}

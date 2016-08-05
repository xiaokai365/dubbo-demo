package org.zookeeper.zkclient.lock;

public class Test01 {
	// 线程个数
	private final static int THREADNUM = 10;

	public static void main(String[] args) {
		for (int i = 0; i < THREADNUM; i++) {
			final ZkClientDistributedLock lock = new ZkClientDistributedLock(i+1);
			new Thread(){
				public void run(){
					lock.center();
				}
			}.start();
		}
		
		try {
			Thread.sleep(5555555);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

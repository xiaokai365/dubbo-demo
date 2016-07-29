package org.zookeeper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Acl;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.mysql.fabric.proto.xmlrpc.DigestAuthentication;

public class ZookeeperTestAcl implements Watcher {

	private static final String zooipaddress = "192.168.186.138:2181";
	private static final int timeout = 5000;
	private static ZooKeeper zk;

	public static void main(String[] args) throws IOException, InterruptedException {
		zk = new ZooKeeper(zooipaddress, timeout, new ZookeeperTestAcl());
		System.out.println(zk.getState());
		Thread.sleep(500000);
	}

	public void process(WatchedEvent arg0) {
		System.out.println(arg0);

		try {
			try {
				ACL aclip = new ACL(Perms.READ, new Id("ip", "192.168.186.1"));
				ACL acldigest = new ACL(Perms.READ | Perms.WRITE,
						new Id("digest", DigestAuthenticationProvider.generateDigest("snail:123456")));
				List<ACL> alcs = new ArrayList<ACL>();
				alcs.add(aclip);
				alcs.add(acldigest);
				zk.create("/snail000", "432".getBytes(), alcs, CreateMode.PERSISTENT);
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

}

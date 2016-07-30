package org.zookeeper.zkclient.masterslave;

import java.io.Serializable;

public class RunningData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long cid;
	private String name;
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}

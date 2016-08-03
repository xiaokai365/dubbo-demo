package org.zookeeper.zkclient.subscribe;

import java.io.Serializable;


public class Serverconfig implements Serializable{

	private String ipaddress;
	private String username;
	private String userpasss;
	
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpasss() {
		return userpasss;
	}
	public void setUserpasss(String userpasss) {
		this.userpasss = userpasss;
	}
	@Override
	public String toString() {
		return "Serverconfig [ipaddress=" + ipaddress + ", username=" + username + ", userpasss=" + userpasss + "]";
	}
	
	
	
}

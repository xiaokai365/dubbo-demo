package org.zookeeper.zkclient;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.zookeeper.pojo.User;

public class ZkClientDemo01 {

		public static void main(String[] args) {
			//-1create 
			ZkClient  zkclient=new ZkClient("192.168.186.138:2181", 5000, 5000, new SerializableSerializer());
			System.out.println("zk连接正常");
			/*User  user=new User();
			user.setId(111);
			user.setName("hello world");
			//可以直接存java对象   主要是SerializableSerializer的支持
			String path=zkclient.create("/yyy", user, CreateMode.PERSISTENT);
			System.out.println(path);*/
			
			
			//2读取  直接返回obj
		/*	User  result =zkclient.readData("/yyy");
			System.out.println(result.getName());*/
			//3更新
			//zkclient.writeData("/yyy", new User(22,"撒谎的"));
			//4删除
			/*boolean flag=zkclient.delete("/yyy");
			boolean  flag2=  zkclient.deleteRecursive("/yyy");//删除节点及下面的子节点
			System.out.println(flag);
			System.out.println(flag2);*/
			//5读取子节点
			/*List<String> datas=  zkclient.getChildren("/snail");
			for(String data:datas){
				System.out.println(data);
			}*/
			//6判断节点是否存在
			/*boolean flag=zkclient.exists("/snail/demo0144");
			System.out.println(flag);*/
			
			
			
		}
	
}

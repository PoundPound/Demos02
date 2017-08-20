/**    
 * @Title: ZkClientWatcher1.java  
 * @Package com.demo.helloworld.zkclient.watcher  
 * @Description: TODO 
 * @author lewis lianfenxiang@163.com
 * @date 2017年8月20日 上午8:22:32  
 * @version V1.0    
 */
package com.demo.helloworld.zkclient.watcher;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**  
 * @ClassName: ZkClientWatcher1  
 * @Description: TODO 
 * @author lewis lianfenxiang@163.com
 * @date 2017年8月20日 上午8:22:32  
 *    
 */
public class ZkClientWatcher1
{
	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.31.178:2181,192.168.31.203:2181,192.168.31.204:2181";
	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;// ms

	public static void main(String[] args) throws Exception
	{
		ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 5000);

		// 对父节点添加监听子节点变化。
		zkc.subscribeChildChanges("/super", new IZkChildListener()
		{
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception
			{
				System.out.println("parentPath: " + parentPath);
				System.out.println("currentChilds: " + currentChilds);
			}
		});

		Thread.sleep(3000);

		zkc.createPersistent("/super");
		Thread.sleep(1000);

		zkc.createPersistent("/super" + "/" + "c1", "c1内容");
		Thread.sleep(1000);

		zkc.createPersistent("/super" + "/" + "c2", "c2内容");
		Thread.sleep(1000);

		zkc.delete("/super/c2");
		Thread.sleep(1000);

		zkc.deleteRecursive("/super");
		Thread.sleep(Integer.MAX_VALUE);

	}
}

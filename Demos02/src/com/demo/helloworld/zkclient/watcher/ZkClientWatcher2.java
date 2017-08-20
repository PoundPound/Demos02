/**    
 * @Title: ZkClientWatcher2.java  
 * @Package com.demo.helloworld.zkclient.watcher  
 * @Description: TODO 
 * @author lewis lianfenxiang@163.com
 * @date 2017年8月20日 上午8:22:40  
 * @version V1.0    
 */
package com.demo.helloworld.zkclient.watcher;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**  
 * @ClassName: ZkClientWatcher2  
 * @Description: TODO 
 * @author lewis lianfenxiang@163.com
 * @date 2017年8月20日 上午8:22:40  
 *    
 */
public class ZkClientWatcher2
{
	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.31.178:2181,192.168.31.203:2181,192.168.31.204:2181";
	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;// ms

	public static void main(String[] args) throws Exception
	{
		ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 5000);

		zkc.createPersistent("/super", "1234");

		// 对父节点添加监听子节点变化。
		zkc.subscribeDataChanges("/super", new IZkDataListener()
		{
			@Override
			public void handleDataDeleted(String path) throws Exception
			{
				System.out.println("删除的节点为:" + path);
			}

			@Override
			public void handleDataChange(String path, Object data) throws Exception
			{
				System.out.println("变更的节点为:" + path + ", 变更内容为:" + data);
			}
		});

		Thread.sleep(3000);
		zkc.writeData("/super", "456", -1);
		Thread.sleep(1000);

		zkc.delete("/super");
		Thread.sleep(Integer.MAX_VALUE);

	}
}

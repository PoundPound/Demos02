/**    
 * @Title: CuratorBase.java  
 * @Package com.demo.helloworld.curator.base  
 * @Description: TODO 
 * @author lewis lianfenxiang@163.com
 * @date 2017年8月20日 上午8:26:46  
 * @version V1.0    
 */
package com.demo.helloworld.curator.base;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**  
 * @ClassName: CuratorBase  
 * @Description: TODO 
 * @author lewis lianfenxiang@163.com
 * @date 2017年8月20日 上午8:26:46  
 *    
 */
public class CuratorBase
{
	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.31.178:2181,192.168.31.203:2181,192.168.31.204:2181";
	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;// ms

	public static void main(String[] args) throws Exception
	{

		// 1 重试策略：初试时间为1s 重试10次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
		// 2 通过工厂创建连接
		CuratorFramework cf = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR)
				.sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy)
				// .namespace("super")
				.build();
		// 3 开启连接
		cf.start();

		// System.out.println(States.CONNECTED);
		// System.out.println(cf.getState());

		// 新加、删除
		// 4 建立节点 指定节点类型（不加withMode默认为持久类型节点）、路径、数据内容
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c1","c1内容".getBytes());
		// 5 删除节点
//		cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");

		// 读取、修改
		// 创建节点 //
//		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c1", "c1内容".getBytes());
//		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c2", "c2内容".getBytes());
		// 读取节点 
//		String ret1 = new String(cf.getData().forPath("/super/c2"));
//		System.out.println(ret1);
		// 修改节点 
//		cf.setData().forPath("/super/c2", "修改c2内容".getBytes());
//		String ret2 = new String(cf.getData().forPath("/super/c2"));
//		System.out.println(ret2);

		// 绑定回调函数
		// 回调函数是异步执行
		// curator跟zkClient与原声API不同，这里的回调是在client端内存中存有一个cache缓存，当节点发生变更就与服务器对比
		// 原生API是不停的去注册
//		ExecutorService pool = Executors.newCachedThreadPool();
//		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).inBackground(new BackgroundCallback()
//		{
//			@Override
//			public void processResult(CuratorFramework cf, CuratorEvent ce) throws Exception
//			{
//				System.out.println("code:" + ce.getResultCode());
//				System.out.println("type:" + ce.getType());
//				System.out.println("线程为:" + Thread.currentThread().getName());
//			}
//		}, pool).forPath("/super/c3", "c3内容".getBytes());
//		Thread.sleep(Integer.MAX_VALUE);

		// 读取子节点getChildren方法 和 判断节点是否存在checkExists方法
//		List<String> list = cf.getChildren().forPath("/super");
//		for (String p : list)
//		{
//			System.out.println(p);
//		}

//		Stat stat = cf.checkExists().forPath("/super/c3");
//		System.out.println(stat);

//		Thread.sleep(2000);
//		cf.delete().deletingChildrenIfNeeded().forPath("/super");
//		cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");

	}
}

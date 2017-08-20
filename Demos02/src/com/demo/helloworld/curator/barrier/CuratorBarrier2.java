/**    
 * @Title: CuratorBarrier2.java  
 * @Package com.demo.helloworld.curator.barrier  
 * @Description: TODO 
 * @author lewis lianfenxiang@163.com
 * @date 2017年8月20日 上午8:26:27  
 * @version V1.0    
 */
package com.demo.helloworld.curator.barrier;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**  
 * @ClassName: CuratorBarrier2  
 * @Description: TODO 
 * @author lewis lianfenxiang@163.com
 * @date 2017年8月20日 上午8:26:27  
 *    
 */
public class CuratorBarrier2
{
	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.31.178:2181,192.168.31.203:2181,192.168.31.204:2181";
	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;// ms

	static DistributedBarrier barrier = null;

	public static void main(String[] args) throws Exception
	{

		for (int i = 0; i < 5; i++)
		{
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
						CuratorFramework cf = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR)
								.sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy).build();
						cf.start();
						barrier = new DistributedBarrier(cf, "/super");
						System.out.println(Thread.currentThread().getName() + "设置barrier!");
						barrier.setBarrier(); // 设置
						barrier.waitOnBarrier(); // 等待
						System.out.println("---------开始执行程序----------");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}, "t" + i).start();
		}

		Thread.sleep(5000);
		barrier.removeBarrier(); // 释放

	}
}

/**    
 * @Title: CuratorAtomicInteger.java  
 * @Package com.demo.helloworld.curator.atomicinteger  
 * @Description: TODO 
 * @author lewis lianfenxiang@163.com
 * @date 2017年8月20日 上午8:25:33  
 * @version V1.0    
 */
package com.demo.helloworld.curator.atomicinteger;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**  
 * @ClassName: CuratorAtomicInteger  
 * @Description: TODO 
 * @author lewis lianfenxiang@163.com
 * @date 2017年8月20日 上午8:25:33  
 *    
 */
public class CuratorAtomicInteger
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
				.sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy).build();
		// 3 开启连接
		cf.start();
		// cf.delete().forPath("/super");

		// 4 使用DistributedAtomicInteger
		DistributedAtomicInteger atomicIntger = new DistributedAtomicInteger(cf, "/super", new RetryNTimes(3, 1000));

		AtomicValue<Integer> value = atomicIntger.add(1);
//		atomicIntger.get();
		System.out.println(value.succeeded());
		System.out.println(value.postValue()); // 最新值
		System.out.println(value.preValue()); // 原始值

	}
}

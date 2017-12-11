package io.bnio.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExecutorPool {
	
	private ExecutorService executorService;
//	corePoolSize 
//	the number of threads to keep in the pool, 
//	even if they are idle, unless allowCoreThreadTimeOut is set
//	maximumPoolSize 
//	the maximum number of threads to allow in the pool
//	keepAliveTime 
//	when the number of threads is greater than the core, 
//	this is the maximum time that excess idle threads will wait for new tasks before terminating.
//	unit 
//	the time unit for the keepAliveTime argument
//	workQueue 
//	the queue to use for holding tasks 
//	before they are executed. 
//	This queue will hold only the Runnable tasks submitted by the execute method.
	public TimeServerHandlerExecutorPool(int maxPoolSize, int queueSize){
		executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 
				maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
	}
	
	public void execute(Runnable task){
		executorService.execute(task);
	}
}

package demo.sst.code.customandproducer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		//建立缓存区
		BlockingDeque<PCData> queue = new LinkedBlockingDeque<PCData>(10);
		Producer producer1 = new Producer(queue);
		Producer producer2 = new Producer(queue);
		Producer producer3 = new Producer(queue);

		Consumer consumer1 = new Consumer(queue);
		Consumer consumer2 = new Consumer(queue);
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(producer1);
		executorService.execute(producer2);
		executorService.execute(producer3);
		executorService.execute(consumer1);
		executorService.execute(consumer2);
		Thread.sleep(5* 1000);
		executorService.shutdownNow();
		while(queue.getFirst() != null){
			System.out.println("剩余===============" + queue.take().getData());
		}
	}

}

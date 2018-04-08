package demo.sst.code.customandproducer;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {

	private volatile boolean isRunning = true;
	private BlockingDeque<PCData> queue;
	private static AtomicInteger count = new AtomicInteger();
	private static final int SLEEPTIME = 1000;

	public Producer(BlockingDeque<PCData> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		PCData data = null;
		Random r = new Random();
		
		System.out.println("Start produce id = " + Thread.currentThread().getId());
		
		try {
			while(isRunning){
				Thread.sleep(r.nextInt(SLEEPTIME));
				data = new PCData(count.incrementAndGet());
				System.out.println(data + " is put into queue");
				if(!queue.offer(data,2,TimeUnit.SECONDS)){
					System.err.println("fail to put data:" + data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}

	}

}

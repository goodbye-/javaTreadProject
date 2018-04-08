package demo.sst.code.customandproducer;

import java.util.Random;
import java.util.concurrent.BlockingDeque;

public class Consumer implements Runnable {

	private BlockingDeque<PCData> queue;
	private static final int SLEEPTIME = 1000;

	public Consumer(BlockingDeque<PCData> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		System.out.println("Start Consumer id = " + Thread.currentThread().getId());
		Random r = new Random();
		try {
			while (true) {
				PCData data = queue.take();
				if (null != data) {
					int re = data.getData() * data.getData(); // 计算平方
					System.out.println(data.getData() + "的平方是：" + re);
					Thread.sleep(r.nextInt(SLEEPTIME));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}

	}

}

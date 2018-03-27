package demo.sst.code;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 76740
 * CountDownLatch的参数意思是要等待10个线程(改成11个这个程序就不对了)
 * countdown()方法是10-1 就是一个已经完成准备
 * await()是等待10都countdown之后才能继续往下执行，如果不写的话就直接执行了
 *
 */
public class CountDownLatchDemo implements Runnable {
	static final CountDownLatch end = new CountDownLatch(10);
	static final CountDownLatchDemo demo = new CountDownLatchDemo();

	@Override
	public void run() {

		try {
			Thread.sleep(new Random().nextInt(3) * 1000);
			System.out.println("check complete");
			end.countDown();
			System.out.println("计时结束");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			executorService.submit(demo);
		}
		// 等待检查
		end.await();
		// ·发射火箭
		System.out.println("Fire!");
		executorService.shutdown();
	}
}
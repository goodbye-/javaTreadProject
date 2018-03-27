package demo.sst.code;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
	public static Object u = new Object();
	static ChangeObjectThread t1 = new ChangeObjectThread("t1");
	static ChangeObjectThread t2 = new ChangeObjectThread("t2");

	public static class ChangeObjectThread extends Thread {
		public ChangeObjectThread(String name) {
			super.setName(name);
		}

		public void run() {
			synchronized (u) {
				System.out.println("in " + getName());
				LockSupport.park();
			}
		}
	}

	public static void main(String args[]) throws InterruptedException {
		t1.start();
		Thread.sleep(100);
		t2.start();
		LockSupport.unpark(t1);
		LockSupport.unpark(t2);//解决了这句话可能发生在t2中park方法之前的问题，其实类似于许可机制，如果unpark先发生，那么t2
		//的park()方法就会立刻返回，他们操作的是同一个许可-permit
		t1.join();
		t2.join();
	}
}
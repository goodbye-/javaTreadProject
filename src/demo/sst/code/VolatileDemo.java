package demo.sst.code;

public class VolatileDemo {

	private  static int number;
	private  volatile static boolean ready;

	private static class ReaderTread extends Thread {
		@Override
		public void run() {
			while(!ready);
			System.out.println(number);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new ReaderTread().start();
		Thread.sleep(2000);
		number = 43;
		ready = true;
		Thread.sleep(10000);
	}
}

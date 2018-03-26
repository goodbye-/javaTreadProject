package demo.sst.code;

/**
 * @author 76740
 *
 *	错误原因就是integer是会变得
 */
public class IntegerSyncDemo implements Runnable{
	
	private static Integer i = 0;
	static IntegerSyncDemo instance = new IntegerSyncDemo();

	/*@Override
	public synchronized void run() {
		for(int j=0;j<10000;j++){
			synchronized (i) {
				i++;
			}
		}
		
	}*/
	
	@Override
	public void run() {
		for(int j=0;j<10000;j++){
			synchronized (instance) {
				i++;
			}
		}
		
	}
	
/*	@Override  error
	public void run() {
		for(int j=0;j<10000;j++){
			synchronized (i) {
				i++;
			}
		}
		
	}*/
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
		
	}

}

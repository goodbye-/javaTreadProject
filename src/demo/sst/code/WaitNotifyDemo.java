package demo.sst.code;

/**
 * @author shui
 *
 */
public class WaitNotifyDemo {
    final static Object object = new Object();

    public static class T1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T1 start!");
                try {
                    System.out.println(System.currentTimeMillis() + ":T1 wait for object!");
                    object.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":T1 end!");
            }
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T2 start! notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis() + ":T2 end!");
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
    }
    
    /**
     *  1522060379917:T1 start!
        1522060379917:T1 wait for object!
        1522060379917:T2 start! notify one thread
        1522060379918:T2 end!
        1522060381918:T1 end!
        
        t2虽然唤醒了t1，但是t1还是要获取锁，也就是在t2执行完之后才可以继续执行
        
     * 
     * 
     * */

}

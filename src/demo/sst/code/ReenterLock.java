package demo.sst.code;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock(); //static 不是必须的
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            lock.lock();
            //lock.lock();  可以获取两次
            try {
                i++;
            } finally{
                lock.unlock();
                //lock.unlock();
            }
        }
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        ReenterLock tl = new ReenterLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }

}

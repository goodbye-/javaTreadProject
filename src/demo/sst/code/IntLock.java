package demo.sst.code;

import java.util.concurrent.locks.ReentrantLock;

public class IntLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    /**
     * @param lock
     *            控制枷锁顺序，方便构造死锁
     */
    public IntLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try{
            if(lock == 1){
                lock1.lockInterruptibly();
                System.out.println("lock1");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    System.out.println("=============");
                }
                lock2.lockInterruptibly();
                System.out.println("1执行完毕");
            }else{
                System.out.println("lock2");
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    System.out.println("22222222222222");
                }
                lock1.lockInterruptibly();//t2中断后 会放弃申请lock1，同事释放lock2
                System.out.println("2执行完毕");
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            if(lock1.isHeldByCurrentThread())
                  lock1.unlock();
            if(lock2.isHeldByCurrentThread())
                lock2.unlock();
            System.out.println(Thread.currentThread().getId() + ": exit");
        }
        
    }
    
    public static void main(String[] args) throws InterruptedException {
        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start(); t2.start();
        //t1.join();t2.join();
        Thread.sleep(1000);
        //中断一个线程
        t2.interrupt();
        
    }

}

package demo.sst.code;

public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            public void run() {
                while(true){
                    System.out.println("执行一次。。。。。。。。。。。");
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("interrupted！");
                        break;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("Interrupted when sleep");
                        //设置中断状态
                        Thread.currentThread().interrupt();//sleep方法由于中断抛出异常，会清除中断标记
                        Thread.yield();//愿意放弃cpu资源
                    }
                }
            };
        };
        t1.start();
        Thread.sleep(10000);
        t1.interrupt();
    }
}

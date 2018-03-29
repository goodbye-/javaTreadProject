package demo.sst.code;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParseDateThreadLocalDemo {
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            try {
                if (threadLocal.get() == null) {
                    System.out.println("set " + i); //set执行前10次
                    threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date date = threadLocal.get().parse("2017-05-06 12:33:" + i % 60);
                System.out.println(i + ":" + date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            //executorService.execute(new ParseDate(i)); //set 10次
            new ParseDate(i).run();//为什么只set一次?因为你他吗的用的是run方法！
            new Thread(new ParseDate(i)).start();//set 100次
        }
    }
}
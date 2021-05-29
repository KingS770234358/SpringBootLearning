import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static int i=0;
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();
    public static class oddsThread extends Thread{
        @Override
        public void run(){
            while(true){
                try{
                    Test.lock.lock();
                    if(Test.i%2==0){
                        condition.await();
                    }
                    System.out.println(Thread.currentThread() + " " + i);
                    i++;
                    Thread.sleep(1);
                    condition.signal();
                }catch(Exception e){

                }finally{
                    Test.lock.unlock();
                }
            }
        }
    }
    public static class evensThread extends Thread{
        @Override
        public void run(){
            while(true){
                try{
                    Test.lock.lock();
                    if(Test.i%2!=0){
                        condition.await();
                    }
                    System.out.println(Thread.currentThread() + " " + i);
                    i++;
                    Thread.sleep(1000);
                    condition.signal();
                }catch(Exception e){

                }finally{
                    lock.unlock();
                }
            }
        }
    }
    public static void main(String[] args) {
        Thread odds = new oddsThread();
        Thread evens = new evensThread();
        odds.start();
        evens.start();
    }
}

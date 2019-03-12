import java.util.concurrent.*;

public class semaphoreBarrier implements Barrier
{
    private Semaphore sem; 
    private final Object lock = new Object();

    public semaphoreBarrier(int N)
    {   
        sem = new Semaphore(N-1);
    }   
    public void arriveAndWait()
    {   
        synchronized(lock) { 
            if (sem.tryAcquire()) {
                try{
                    System.out.println("waiting");
                    System.out.flush();
                    lock.wait();
                }   
                catch(InterruptedException e){ 
                    System.out.println("wait interrupted!");
                }   
                System.out.println("incrementing");
                System.out.flush();
                sem.release();
            }   
            else {
                System.out.println("releasing");
                System.out.flush();
                lock.notifyAll();
            }   
        }   
    }   
}

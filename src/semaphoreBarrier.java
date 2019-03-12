import java.util.concurrent.*;

public class semaphoreBarrier implements Barrier
{
    private Semaphore sem;
    private int n;
    private final Object lock = new Object();

    public semaphoreBarrier(int N)
    {   
        sem = new Semaphore(N-1);
        n = N; 
    }   
    
    public void arriveAndWait()
    {   
        synchronized(lock) { 
            if (sem.tryAcquire()) {
                try{
                    lock.wait();
                }   
                catch(InterruptedException e){ 
                    System.out.println("wait interrupted!");
                }   
            }   
            else {
                lock.notifyAll();
                sem.release(n-1);
            }   
        }   
    }   
}

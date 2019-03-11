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
                    lock.wait();
                }
                catch(InterruptedException e){
                    System.out.println("wait interrupted!");
                }
                sem.release();
            }   
            else {
                lock.notifyAll();
            }
        }
	}
}

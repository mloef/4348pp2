import java.util.concurrent.*;

public class semaphoreBarrier implements Barrier
{
    private Semaphore sem; 

	public semaphoreBarrier(int N)
	{
        sem = new Semaphore(N-1);
	}
	public void arriveAndWait()
	{
	    if (sem.tryAcquire()) {
            wait();
            sem.release()
        }   
        else notifyAll();	
	}
}
import java.util.concurrent.*;

class semBarrier extends Barrier {
    private Semaphore sem; 
    
    semBarrier(int n) {
        sem = new Semaphore(n-1);
    }

    public void arriveAndWait() {
        if (sem.tryAcquire()) {
            wait();
            sem.release()
        }
        else notifyAll();
    }
}

class monBarrier extends Barrier {
    private int count; 
    private int numThreads; 
    
    monBarrier(int n) {
        count = 0;
        numThreads = n;
    }

    public void syncronized arriveAndWait() {
        count++;
        if (count == numThreads) {
            count = 0;
            signalAll();
        }
        else wait();
    }
}


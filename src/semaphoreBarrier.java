import java.util.concurrent.*;

public class semaphoreBarrier implements Barrier
{
    private Semaphore[] sems;
    private Semaphore atomInt;
    int n; 
    int index; 
    int gen;

    public semaphoreBarrier(int N)
    {   
        sems = new Semaphore[N-1];
        for (int i = 0; i < N-1; ++i) {
            sems[i] = new Semaphore(0, true);
        }
        atomInt = new Semaphore(1, true);
        index = 0;
        n = N; 
        gen = 0;
    }   

    public void arriveAndWait()
    {
        try{atomInt.acquire();}
        catch(InterruptedException e){
            //System.out.println("wait interrupted!");
        }
        
        //System.out.println("Thread " + Thread.currentThread().getId() + " entering");
        //System.out.flush();
        int t = index;
        index += 1;
        
        if (t < n-1) { 
            //System.out.println("Thread " + Thread.currentThread().getId() + " in wait");
            //System.out.flush();
            atomInt.release();
            try{sems[t].acquire();}
            catch(InterruptedException e){
                //System.out.println("wait interrupted!");
            }
            //System.out.println("Thread " + Thread.currentThread().getId() + " released");
            //System.out.flush();
        } 
        
        else {
            index = 0;
            //System.out.println("Thread " + Thread.currentThread().getId() + " in releasing");
            //System.out.flush();
            for (int i = 0; i < n-1; ++i) {
                sems[i].release();
            }
            atomInt.release();
            gen++;
        }
    }
}

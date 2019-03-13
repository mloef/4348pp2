import java.util.concurrent.*;

public class semaphoreBarrier implements Barrier
{
    private Semaphore[] sems;
    private Semaphore atomInt;
    int n; 
    int index; 

    public semaphoreBarrier(int N)
    {   
        sems = new Semaphore[N-1];
        for (int i = 0; i < N-1; ++i) {
            sems[i] = new Semaphore(0);
        }
        atomInt = new Semaphore(1);
        index = 0;
        n = N; 
    }   

    public void arriveAndWait()
    {
        try{atomInt.acquire();}
        catch(InterruptedException e){
            System.out.println("wait interrupted!");
        }
        
        int t = index;
        index += 1;
        atomInt.release();
        
        if (t < n-1) { 
            try{sems[t].acquire();}
            catch(InterruptedException e){
                System.out.println("wait interrupted!");
            }
        } 
        
        else {
            for (int i = 0; i < n-1; ++i) {
                sems[i].release();
            }
            index = 0; 
        }
    }
}

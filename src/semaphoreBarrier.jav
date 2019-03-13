import java.util.concurrent.*;
import java.util.Stack;

public class semaphoreBarrier implements Barrier
{
    private Stack<Semaphore> sems = new Stack<Semaphore>();
    private Semaphore atomInt;
    int n; 

    public semaphoreBarrier(int N)
    {   
        atomInt = new Semaphore(1);
        n = N; 
    }   

    public void arriveAndWait()
    {
        try{atomInt.acquire();}
        catch(InterruptedException e){
            System.out.println("wait interrupted!");
        }
        
        int t = sems.size();
        System.out.println("Thread " + t + " entering");
        System.out.flush();
        
        if (t < n-1) { 
            sems.push(new Semaphore(0)); 
            atomInt.release();
            System.out.println("Thread " + t + " in wait");
            System.out.flush();
            try{sems.peek().acquire();}
            catch(InterruptedException e){
                System.out.println("wait interrupted!");
            }
        } 
        
        else {
            while (sems.size() != 0) {
                sems.peek().release();
                sems.pop();
            }
            atomInt.release();
            System.out.println("Thread " + t + " in release");
            System.out.flush();
        }
    }
}

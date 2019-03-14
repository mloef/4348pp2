import java.util.concurrent.*;

public class semaphoreBarrier implements Barrier
{
    //sems holds n-1 semaphores
    //atomInt ensures that index is accessed in a thread-safe manner
    //n is the number of threads
    //index controls iteration of the array that the thread is assigned to
    private Semaphore[] sems;
    private Semaphore atomInt;
    int n; 
    int index; 

    public semaphoreBarrier(int N)
    {
        //creates and populates sems -- an array of semaphores, which has FIFO enforced
        sems = new Semaphore[N-1];
        for (int i = 0; i < N-1; ++i) {
            sems[i] = new Semaphore(0, true);
        }
        //creates atomInt and other variables
        atomInt = new Semaphore(1, true);
        index = 0;
        n = N; 
    }

    public void arriveAndWait()
    {
        try{atomInt.acquire();}
        catch(InterruptedException e){
            System.out.println("wait interrupted!");
        }

        //t is the index of the first available semaphore
        int t = index;
        index += 1;
        
        if (t < n-1){
            atomInt.release();
            //acquire semaphore t
            try{sems[t].acquire();}
            catch(InterruptedException e){
                System.out.println("wait interrupted!");
            }
        } 
        
        else {
            //when all semaphores are taken, reset the array of semaphores and reset index
            index = 0;
            for (int i = 0; i < n-1; ++i) {
                sems[i].release();
            }
            atomInt.release();
        }
    }
}

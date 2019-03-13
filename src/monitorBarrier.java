public class monitorBarrier implements Barrier
{
    private int count;
    private int numThreads;
    
    public monitorBarrier(int N)
    {
        //assigns proper values to private variables
        numThreads = N;
        count = 0;
    }
    
    public synchronized void arriveAndWait()
    {
        //increments the counter of arrived threads
        count++;

        //when all threads have arrived
        if (count == numThreads) {
            //reset counter and resume all threads
            count = 0;
            notifyAll();
        }   
        else{
            //when not all threads have arrived, freeze current thread
            try{wait();}
            catch(InterruptedException e){
                System.out.println("wait interrupted!");
            }
        }
    }
}

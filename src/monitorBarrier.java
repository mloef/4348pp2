public class monitorBarrier implements Barrier
{
	private int count;
	private int numThreads;
	/*
	 *	Private members and methods here
	 */
	public monitorBarrier(int N)
	{
		/*
		 *	Code for your constructor here
		 */
		numThreads = N;
		count = 0;
	}
	public synchronized void arriveAndWait()
	{
		count++;
        if (count == numThreads) {
            count = 0;
            notifyAll();
        }   
        else{
        	try{
        		wait();
        	}
        	catch(InterruptedException e){
        		System.out.println("wait interrupted!");
        	}
        }
	}
}

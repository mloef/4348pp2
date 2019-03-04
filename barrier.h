#include <semaphore.h>

class Barrier_sem
{
    private:
    sem_t sem;

    public:
    Barrier_sem(int num_threads)
    {
        int r = sem_init(&sem, 0, num_threads);
        if (r == -1)
            printf("shits fukt yo\n");
    }

    void arriveAndWait() {
        sem_wait(&sem);
        int sval;
        sem_getvalue(&sem, &sval); 
        while (sval > 0) {
            sem_getvalue(&sem, &sval); 
            //std::cout << sval << '\n';
        }
        //std::cout << "Barrier: thread exited\n";
        sem_post(&sem);
    }

};

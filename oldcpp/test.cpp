#include <pthread.h>
#include <iostream>
#include <sstream>
#include <vector>
#include "barrier.h"

static int NUM_THREADS = 10;
Barrier_sem b = Barrier_sem(NUM_THREADS);

void *worker(void *t_id){
    std::stringstream s;
    long thread_id = (long) t_id;
    
    s.str("");
    s << "thread " << thread_id << " created\n";
    std::cout << s.str();
    
    b.arriveAndWait();
    
    s.str("");
    s << "thread " << thread_id << " exited\n";
    std::cout << s.str();
}

int main(){
    pthread_t threads[NUM_THREADS];
    for (int t = 0; t<NUM_THREADS; ++t){
        int rc = pthread_create(threads + t, NULL, worker, (void*) t); 
        if (rc){
            std::stringstream s;
            s.str("");
            s << "error creating thread " << t << '\n';
            std::cerr << s.str();
        }   
    }   
}

# Scheduling-Algorithms
A Java implementation of the various preemptive and non-preemptive OS scheduling algorithms, including FCFS, SPN, HRRN, RR, SRT, and FB.

## Preemptive Algorithms
### FCFS (First Come First Serve)
FCFS schedules different tasks according to their arrivals time. Since tasks’ arrival times are in ascending order, the program assigns tasks with earlier arrival times first. The interesting part of this algorithm is how to update the next task’s starting time. The program compares the current task's finish time with the next task's arrival time. If next task's arrival time is larger, update starting time to the next task's arrival time, otherwise, update starting time to the current task's finish time.

### SPN (Shortest Process Next)
SPN schedules different tasks according to their processing time. It will schedule the task with shortest processing time first. The program will select the next shorting processing time task once the current task finishes. During the seeking of the next task, the program compare the current time with the arrival time of unfinished tasks. If unfinished tasks don’t arrive yet, the task should not be assigned. The interesting part of this algorithm is that I used a Boolean array to store the status of the task. If a task finishes execution, the corresponding Boolean value will be updated to true.

### HRRN (Highest Response Ratio Next)
HRRN schedules different tasks according to their response ratio, which can be calculated by a formula, response ratio = (waiting time + service time) / service time. It will schedule the task with the highest response ratio first. The task selection process is quite similar with SPN algorithm except HRRN uses response ratio as the selection criteria.

## Non-Preemptive Algorithms
### RR (Round Robin)
RR gives every task a slice of time to execute. Here the slice(quantum) is 1. When implementing RR algorithm, I used a queue to record the order of tasks. The task will execute in order. If the task doesn’t finish after execution of one time slice, it will re-join the queue waiting to be executed. However, if a new tasks arrives at the same time the current executing task times out, the new process is added to the tail of the queue and then the current unfinished process timing out is added behind it.

### SRT (Shortest Remaining Time)
SRT is the pre-emptive version of SPN. It may pre-empt a new arrival task if it’s remaining service time is smaller than the current task. Also, it will schedule the new task when current task finishes. However, if the new task doesn’t arrive yet, it will wait until the new task arrives. When implementing SRT algorithm, I kept a list of the remaining time of every tasks.

### FB (Feedback)
FB assigns the newly arrival task to the highest-priority queue, after the current task is pre-empted to a new task, the current task will downgrade to the next level’s priority queue. However, after reaching the lowest-priority queue, it will not go back to the highest-priority queue. Every time the algorithm schedules a task, it will start to seek the task from the highest-priority queue to the lowest-priority queue.

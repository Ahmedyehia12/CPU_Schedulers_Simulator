import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestRemainingTimeFirst implements SchedulingAlgorithm{ // same as ShortestJobFirst but preemptive, we need to solve the starvation problem
    private PriorityQueue<Process> readyQueue;
    private List<Process> processes;
    public int totalWaitingTime = 0;
    public int contextSwitchTime = 0;
    public int totalTurnAroundTime = 0;
    public ShortestRemainingTimeFirst(List<Process> processes , int contextSwitchTime) {
        this.contextSwitchTime = contextSwitchTime;
        readyQueue = new  PriorityQueue<>(processes.size() , new Comparator<Process>() { // min heap , the process with the shortest burst time will be at the top
            @Override
            public int compare(Process o1, Process o2) {
                return o1.getBurstTime() - o2.getBurstTime();
            }
        });
        this.processes = processes;
    }
    @Override
    public void getExecutionOrder() {
        System.out.println("Execution Order : ");
        readyQueue.clear();
        int time = 0;
        HashMap<String , Integer> added = new HashMap<>(); // to check if the process is already added to the ready queue
        while(added.size()<processes.size()){
            for(Process process : processes){
                if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
                    readyQueue.add(process);
                    added.put(process.getName() , 1);
                }
            }
            if(readyQueue.isEmpty()){
                time++;
                continue;
            }
            Process p = readyQueue.poll();
            int jobTime = p.getBurstTime(); // current remaining time of the process
            for(int i = time ;i<= (time+p.getBurstTime()) ;i++){  // loop from the current time to the current time + the burst time of the process
                for(Process process : processes){ // check if there is a process arrived in this time
                    if(process.getArrivalTime() <= i && !added.containsKey(process.getName())){ // if there is a process arrived in this time and it's not added to the ready queue
                        readyQueue.add(process); // add it to the ready queue
                        added.put(process.getName() , 1); // mark it as added
                    }
                }
                if(!readyQueue.isEmpty()){ // if the ready queue is not empty
                    if(readyQueue.peek().getBurstTime() < jobTime - (i-time)){
                        p.setBurstTime(jobTime - (i-time)); // update the remaining time of the process
                        if(p.getBurstTime() == 0){ // if the process is finished
                            System.out.println(p.getName()); // print the name of the process
                            time = i; // update the current time
                            time += contextSwitchTime; // add the context switch time to the time
                        }
                        else{
                            readyQueue.add(p); // add the process to the ready queue
                            time = i; // update the current time
                            time += contextSwitchTime; // add the context switch time to the time
                        }
                        p = readyQueue.poll(); // poll the process with the shortest remaining time
                        jobTime = p.getBurstTime(); // update the remaining time of the process
                    }
                }
            }
            System.out.println(p.getName()); // print the name of the process
            time += p.getBurstTime(); // update the current time
            time += contextSwitchTime; // add the context switch time to the time
        }
        while(!readyQueue.isEmpty()){
            Process p = readyQueue.poll();
            System.out.println(p.getName());
            time += p.getBurstTime();
            time += contextSwitchTime;
        }
    }
    @Override
    public void getWaitingTime() {

    }

    @Override
    public void getTurnAroundTime() {

    }

    @Override
    public double getAverageWaitingTime() {
        return 0;
    }

    @Override
    public double getAverageTurnAroundTime() {
        return 0;
    }
}

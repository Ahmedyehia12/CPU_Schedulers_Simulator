import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestJobFirst implements SchedulingAlgorithm{
    private PriorityQueue<Process> readyQueue;
    private List<Process> processes;
    public int totalWaitingTime = 0;
    public int contextSwitchTime = 0;
    public int totalTurnAroundTime = 0;
    public ShortestJobFirst(List<Process> processes, int contextSwitchTime) {
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
        HashMap<String , Integer>added = new HashMap<>(); // to check if the process is already added to the ready queue
        for(Process process : processes){
            if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
                readyQueue.add(process);
                added.put(process.getName() , 1);
            }
        }
        while(added.size()<processes.size()){
        for(Process process : processes){
            if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
                readyQueue.add(process);
                added.put(process.getName() , 1);
            }
        }
            Process p = readyQueue.poll(); // poll: returns the head of the queue and removes it
            System.out.println(p.getName());
            time += p.getBurstTime(); // add the burst time of the process to the time
            time += contextSwitchTime; // add the context switch time to the time
        }
        while(!readyQueue.isEmpty()){
            Process p = readyQueue.poll(); // poll: returns the head of the queue and removes it
            System.out.println(p.getName());
            time += p.getBurstTime(); // add the burst time of the process to the time
            time += contextSwitchTime; // add the context switch time to the time
        }
    }

    @Override
    public void getWaitingTime() {
        System.out.println("Waiting Time : ");
        readyQueue.clear(); // clear the ready queue
        int time = 0;
        HashMap<String , Integer>added = new HashMap<>(); // to check if the process is already added to the ready queue
        for(Process process : processes){
            if(process.getArrivalTime() <= time){
                readyQueue.add(process);
                added.put(process.getName() , 1);
            }
        }
        while(added.size() < processes.size()){
            for(Process process : processes){
                if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
                    readyQueue.add(process);
                    added.put(process.getName() , 1);
                }
            }
            Process p = readyQueue.poll(); // poll: returns the head of the queue and removes it
            System.out.println(p.getName() + " : " + (time - p.getArrivalTime()));
            totalWaitingTime += (time - p.getArrivalTime());
            time += p.getBurstTime(); // add the burst time of the process to the time
            time += contextSwitchTime; // add the context switch time to the time
        }
        while(!readyQueue.isEmpty()){
            Process p = readyQueue.poll(); // poll: returns the head of the queue and removes it
            System.out.println(p.getName() + " : " + (time - p.getArrivalTime()));
            totalWaitingTime += (time - p.getArrivalTime());
            time += p.getBurstTime(); // add the burst time of the process to the time
            time += contextSwitchTime; // add the context switch time to the time
        }

    }

    @Override
    public void getTurnAroundTime() {
        System.out.println("Turn Around Time : ");
        readyQueue.clear(); // clear the ready queue
        int time = 0;
        HashMap<String , Integer>added = new HashMap<>(); // to check if the process is already added to the ready queue
        for(Process process : processes){
            if(process.getArrivalTime() <= time){
                readyQueue.add(process);
                added.put(process.getName() , 1);
            }
        }
        while(readyQueue.size() > 0){
            for(Process process : processes){
                if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
                    readyQueue.add(process);
                    added.put(process.getName() , 1);
                }
            }
            Process p = readyQueue.poll(); // poll: returns the head of the queue and removes it
            System.out.println(p.getName() + " : " + (time + p.getBurstTime() - p.getArrivalTime()));
            totalTurnAroundTime += (time + p.getBurstTime() - p.getArrivalTime());
            time += p.getBurstTime(); // add the burst time of the process to the time
            time += contextSwitchTime; // add the context switch time to the time
        }
        while(!readyQueue.isEmpty()){
            Process p = readyQueue.poll(); // poll: returns the head of the queue and removes it
            System.out.println(p.getName() + " : " + (time + p.getBurstTime() - p.getArrivalTime()));
            totalTurnAroundTime += (time + p.getBurstTime() - p.getArrivalTime());
            time += p.getBurstTime(); // add the burst time of the process to the time
            time += contextSwitchTime; // add the context switch time to the time
        }
    }

    @Override
    public double getAverageWaitingTime() {
        return (double)totalWaitingTime / processes.size();

    }

    @Override
    public double getAverageTurnAroundTime() {
        return (double)totalTurnAroundTime / processes.size();
    }
}

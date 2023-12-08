import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestJobFirst implements SchedulingAlgorithm{
    private PriorityQueue<Process> readyQueue;
    private List<Process> processes;
    public int totalWaitingTime = 0;
    public int contextSwitchTime;
    public int totalTurnAroundTime = 0;
    HashMap<Process , Integer>waitingTime = new HashMap<>();
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
        while(added.size()<processes.size()){
        for(Process process : processes){
            if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
                readyQueue.add(process);
                added.put(process.getName() , 1);
            }
        }
            if(!readyQueue.isEmpty()){
            Process p = readyQueue.poll(); // poll: returns the head of the queue and removes it
            System.out.println(p.getName());
            waitingTime.put(p, time - p.getArrivalTime());
            time += p.getBurstTime(); // add the burst time of the process to the time
            time += contextSwitchTime; // add the context switch time to the time
            }
            else{
                time++; // if the ready queue is empty , increment the time by 1
            }
        }
        while(!readyQueue.isEmpty()){
            Process p = readyQueue.poll(); // poll: returns the head of the queue and removes it
            waitingTime.put(p , time - p.getArrivalTime());
            System.out.println(p.getName());
            time += p.getBurstTime(); // add the burst time of the process to the time
            time += contextSwitchTime; // add the context switch time to the time
        }
    }

    @Override
    public void getWaitingTime() {
        totalWaitingTime = 0;
        System.out.println("Waiting Time : ");
        for(Process p : processes){
            System.out.println(p.getName() + " : " + waitingTime.get(p));
        }
    }

    @Override
    public void getTurnAroundTime() {
        System.out.println("Turn Around Time : ");
        for(Process p : processes){
            System.out.println(p.getName() + " : " + waitingTime.get(p) + p.getBurstTime());
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

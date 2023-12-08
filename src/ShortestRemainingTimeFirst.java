import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestRemainingTimeFirst implements SchedulingAlgorithm{ // same as ShortestJobFirst but preemptive
    private PriorityQueue<Process> readyQueue;
    private List<Process> processes;
    HashMap<Process , Integer>currentWaiting = new HashMap<>();
    int limit;
    HashMap<String , Integer>turnAroundTime = new HashMap<>();
    public int contextSwitchTime = 0;
    HashMap<Process , Integer> waitingTime = new HashMap<>();
    public ShortestRemainingTimeFirst(List<Process> processes , int contextSwitchTime , int limit) {
        this.contextSwitchTime = contextSwitchTime;
        readyQueue = new  PriorityQueue<>(processes.size() , new Comparator<Process>() { // min heap , the process with the shortest burst time will be at the top
            @Override
            public int compare(Process o1, Process o2) {
                return o1.getBurstTime() - o2.getBurstTime();
            }
        });
        this.processes = processes;
        this.limit = limit;
        for(Process p : processes){
            turnAroundTime.put(p.getName() , p.getBurstTime());
        }
    }
    public Process getProcessIfLimit(){
        for(Process process : readyQueue){
           if(currentWaiting.containsKey(process) && currentWaiting.get(process) >= limit)
               return process;
        }
        return null;
    }
    public void updateCurrentWaiting(){
        for(Process process : readyQueue){
            if(currentWaiting.containsKey(process))
                currentWaiting.put(process , currentWaiting.get(process) + 1);
            else
                currentWaiting.put(process , 1);
        }
    }
    @Override
    public void getExecutionOrder() {
        System.out.println("Execution Order : ");
        readyQueue.clear();
        waitingTime.clear();
        int time = 0;
        HashMap<String , Integer>added = new HashMap<>();
        while(added.size()<processes.size() || !readyQueue.isEmpty()){
            addNewProcesses(time , added);
            if(!readyQueue.isEmpty()){
                Process p = readyQueue.poll();
                currentWaiting.put(p , 0); // reset the waiting time of the process, as it will enter the cpu
                System.out.println(p.getName() + " entered cpu at : " + time); // print the name of the process
                int i;
                for(i = time ; i<= time + p.getBurstTime() ; i++){
                    addNewProcesses(i , added);
                    if(!readyQueue.isEmpty()){
                        Process p2 = getProcessIfLimit();
                        if(p2 == null){
                            p2 = readyQueue.peek();
                        }
                        else{
                            currentWaiting.put(p2 , 0);
                            p.setBurstTime(p.getBurstTime() - (i - time));
                            if(waitingTime.containsKey(p))
                                waitingTime.put(p , waitingTime.get(p) + time - p.getArrivalTime());
                            else
                                waitingTime.put(p , time - p.getArrivalTime());
                            p.setArrivalTime(i);
                            readyQueue.add(p);
                            p = p2;
                            time = i;
                            readyQueue.remove(p2); // remove the process from the ready queue
                            System.out.println(p.getName() + " entered cpu at : " + i);
                            continue;
                        }
                        if(p2.getBurstTime() < p.getBurstTime() - (i - time)){
                            p.setBurstTime(p.getBurstTime() - (i - time));
                            readyQueue.add(p);
                            if(waitingTime.containsKey(p))
                                waitingTime.put(p , waitingTime.get(p) + time - p.getArrivalTime());
                            else
                                waitingTime.put(p , time - p.getArrivalTime());
                            p.setArrivalTime(i);
                            currentWaiting.put(p , 0);
                            p = readyQueue.poll();
                            currentWaiting.put(p , 0);
                            time = i;
                            System.out.println(p.getName() + " entered cpu at : " + i);
                        }
                    }
                    updateCurrentWaiting();
                }
                if(waitingTime.containsKey(p))
                    waitingTime.put(p , waitingTime.get(p) + time - p.getArrivalTime());
                else
                    waitingTime.put(p , time - p.getArrivalTime());
                time = i-1;
            }
            else{
                time++;
            }
        }
    }

    void addNewProcesses(int time , HashMap<String , Integer> added){
        for(Process process : processes){
            if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
                readyQueue.add(process);
                added.put(process.getName() , 1);
                currentWaiting.put(process , 0);
            }
        }
    }
    @Override
    public void getWaitingTime() {
        System.out.println("Waiting Time : ");
        for(Process process : processes){
            System.out.println(process.getName() + " : " + waitingTime.get(process) + " ms");
        }
    }

    @Override
    public void getTurnAroundTime() {
        System.out.println("Turn Around Time : ");
        for(Process process : processes){
            System.out.println(process.getName() + " : " + (waitingTime.get(process) + turnAroundTime.get(process.getName()) + " ms"));
        }
    }

    @Override
    public double getAverageWaitingTime() {
        double sum = 0;
        for(Process process : processes){
            sum += waitingTime.get(process);
        }
        return sum/processes.size();
    }

    @Override
    public double getAverageTurnAroundTime() {
        double turnAroundTime = 0;
        for(Process process : processes){
            turnAroundTime += waitingTime.get(process) + this.turnAroundTime.get(process.getName());
        }
        return turnAroundTime/processes.size();
    }
}

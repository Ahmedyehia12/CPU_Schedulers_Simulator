import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestRemainingTimeFirst implements SchedulingAlgorithm{ // same as ShortestJobFirst but preemptive
    private PriorityQueue<Process> readyQueue;
    private List<Process> processes;
    public int totalWaitingTime = 0;
    public int contextSwitchTime = 0;
    public int totalTurnAroundTime = 0;
    HashMap<String , Integer> waitingTime = new HashMap<>(); // to store the waiting time for each process
    HashMap<String , Integer> turnAroundTime = new HashMap<>(); // to store the turn around time for each process
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
        waitingTime.clear();
        turnAroundTime.clear();
        int time = 0;
        HashMap<String , Integer> added = new HashMap<>(); // to check if the process is already added to the ready queue
        while(added.size()<processes.size()){
//            for(Process process : processes){
//                if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
//                    readyQueue.add(process);
//                    added.put(process.getName() , 1);
//                }
//            }
            addNewProcesses(time , added);
            if(readyQueue.isEmpty()){
                time++;
                continue;
            }
            Process p = readyQueue.poll();
            int jobTime = p.getBurstTime(); // current remaining time of the process
            for(int i = time ;i<= (time+p.getBurstTime()) ;i++){  // loop from the current time to the current time + the burst time of the process
//                for(Process process : processes){ // check if there is a process arrived in this time
//                    if(process.getArrivalTime() <= i && !added.containsKey(process.getName())){ // if there is a process arrived in this time and it's not added to the ready queue
//                        readyQueue.add(process); // add it to the ready queue
//                        added.put(process.getName() , 1); // mark it as added
//                    }
//                }
                addNewProcesses(i , added);
                if(!readyQueue.isEmpty()){ // if the ready queue is not empty
                    if(readyQueue.peek().getBurstTime()< jobTime - (i-time)){
                        p.setBurstTime(jobTime - (i-time)); // update the remaining time of the process
                     if(p.getBurstTime() == 0){ // if the process is finished // TODO
                            if(waitingTime.containsKey(p.getName())){
                                waitingTime.put(p.getName() , waitingTime.get(p.getName()) + (time - p.getArrivalTime()));
                            }
                            else{
                                waitingTime.put(p.getName() , time - p.getArrivalTime());
                            }
                            System.out.println(p.getName()); // print the name of the process
                            time = i; // update the current time
                            time += contextSwitchTime; // add the context switch time to the time
                        }
                        else{
                            readyQueue.add(p); // add the process to the ready queue
                            if(waitingTime.containsKey(p.getName())){
                                waitingTime.put(p.getName() , waitingTime.get(p.getName()) + (time - p.getArrivalTime()));
                            }
                            else{
                                waitingTime.put(p.getName() , time - p.getArrivalTime());
                            }
                            p.setArrivalTime(i); // update the arrival time of the process
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

    void addNewProcesses(int time , HashMap<String , Integer> added){
        for(Process process : processes){
            if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
                readyQueue.add(process);
                added.put(process.getName() , 1);
            }
        }
    }
    @Override
    public void getWaitingTime() {
        System.out.println("Waiting Time : ");
        readyQueue.clear();
        waitingTime.clear();
        int time = 0;
        HashMap<String , Integer> added = new HashMap<>(); // to check if the process is already added to the ready queue
        while(added.size()<processes.size()){
//            for(Process process : processes){
//                if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
//                    readyQueue.add(process);
//                    added.put(process.getName() , 1);
//                }
//            }
            addNewProcesses(time , added);
            if(readyQueue.isEmpty()){
                time++;
                continue;
            }
            Process p = readyQueue.poll();
            int jobTime = p.getBurstTime(); // current remaining time of the process
            for(int i = time ;i<= (time+p.getBurstTime()) ;i++){  // loop from the current time to the current time + the burst time of the process
//                for(Process process : processes){ // check if there is a process arrived in this time
//                    if(process.getArrivalTime() <= i && !added.containsKey(process.getName())){ // if there is a process arrived in this time and it's not added to the ready queue
//                        readyQueue.add(process); // add it to the ready queue
//                        added.put(process.getName() , 1); // mark it as added
//                    }
//                }
                addNewProcesses(i , added);
                if(!readyQueue.isEmpty()){ // if the ready queue is not empty
                    if(readyQueue.peek().getBurstTime()< jobTime - (i-time)){
                        p.setBurstTime(jobTime - (i-time)); // update the remaining time of the process
                        if(p.getBurstTime() == 0){ // if the process is finished
                            if(waitingTime.containsKey(p.getName())){
                                waitingTime.put(p.getName() , waitingTime.get(p.getName()) + (time - p.getArrivalTime()));
                            }
                            else{
                                waitingTime.put(p.getName() , time - p.getArrivalTime());
                            }
                            time = i; // update the current time
                            time += contextSwitchTime; // add the context switch time to the time
                        }
                        else{
                            readyQueue.add(p); // add the process to the ready queue
                            if(waitingTime.containsKey(p.getName())){
                                waitingTime.put(p.getName() , waitingTime.get(p.getName()) + (time - p.getArrivalTime()));
                            }
                            else{
                                waitingTime.put(p.getName() , time - p.getArrivalTime());
                            }
                            p.setArrivalTime(i); // update the arrival time of the process
                            time = i; // update the current time
                            time += contextSwitchTime; // add the context switch time to the time
                        }
                        p = readyQueue.poll(); // poll the process with the shortest remaining time
                        jobTime = p.getBurstTime(); // update the remaining time of the process
                    }
                }
            }
            if(waitingTime.containsKey(p.getName())){
                waitingTime.put(p.getName() , waitingTime.get(p.getName()) + (time - p.getArrivalTime()));
            }
            else{
                waitingTime.put(p.getName() , time - p.getArrivalTime());
            }
            time += p.getBurstTime(); // update the current time
            time += contextSwitchTime; // add the context switch time to the time
        }
        while(!readyQueue.isEmpty()){
            Process p = readyQueue.poll();
            if(waitingTime.containsKey(p.getName())){
                waitingTime.put(p.getName() , waitingTime.get(p.getName()) + (time - p.getArrivalTime()));
            }
            else{
                waitingTime.put(p.getName() , time - p.getArrivalTime());
            }
            time += p.getBurstTime();
            time += contextSwitchTime;
        }
        for(Process process : processes){
            System.out.println(process.getName() + " : " + waitingTime.get(process.getName()));
        }
    }

    @Override
    public void getTurnAroundTime() {
        System.out.println("Turn around time : ");
        readyQueue.clear();
        waitingTime.clear();
        int time = 0;
        HashMap<String , Integer> added = new HashMap<>(); // to check if the process is already added to the ready queue
        while(added.size()<processes.size()){
//            for(Process process : processes){
//                if(process.getArrivalTime() <= time && !added.containsKey(process.getName())){
//                    readyQueue.add(process);
//                    added.put(process.getName() , 1);
//                }
//            }
            addNewProcesses(time , added);
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
                    if(readyQueue.peek().getBurstTime()< jobTime - (i-time)){
                        p.setBurstTime(jobTime - (i-time)); // update the remaining time of the process
                        if(p.getBurstTime() == 0){ // if the process is finished
                            if(waitingTime.containsKey(p.getName())){
                                waitingTime.put(p.getName() , waitingTime.get(p.getName()) + (time - p.getArrivalTime()));
                            }
                            else{
                                waitingTime.put(p.getName() , time - p.getArrivalTime());
                            }
                            time = i; // update the current time
                            time += contextSwitchTime; // add the context switch time to the time
                        }
                        else{
                            readyQueue.add(p); // add the process to the ready queue
                            if(waitingTime.containsKey(p.getName())){
                                waitingTime.put(p.getName() , waitingTime.get(p.getName()) + (time - p.getArrivalTime()));
                            }
                            else{
                                waitingTime.put(p.getName() , time - p.getArrivalTime());
                            }
                            p.setArrivalTime(i); // update the arrival time of the process
                            time = i; // update the current time
                            time += contextSwitchTime; // add the context switch time to the time
                        }
                        p = readyQueue.poll(); // poll the process with the shortest remaining time
                        jobTime = p.getBurstTime(); // update the remaining time of the process
                    }
                }
            }
            if(waitingTime.containsKey(p.getName())){
                waitingTime.put(p.getName() , waitingTime.get(p.getName()) + (time - p.getArrivalTime()));
            }
            else{
                waitingTime.put(p.getName() , time - p.getArrivalTime());
            }
            time += p.getBurstTime(); // update the current time
            time += contextSwitchTime; // add the context switch time to the time
        }
        while(!readyQueue.isEmpty()){
            Process p = readyQueue.poll();
            if(waitingTime.containsKey(p.getName())){
                waitingTime.put(p.getName() , waitingTime.get(p.getName()) + (time - p.getArrivalTime()));
            }
            else{
                waitingTime.put(p.getName() , time - p.getArrivalTime());
            }
            time += p.getBurstTime();
            time += contextSwitchTime;
        }
        for(Process process : processes){
            System.out.println(process.getName() + " : " + (waitingTime.get(process.getName()) + process.getBurstTime()));
            turnAroundTime.put(process.getName() , (waitingTime.get(process.getName()) + process.getBurstTime()) );
        }

    }

    @Override
    public double getAverageWaitingTime() {
        double sum = 0;
        for(Process process : processes){
            sum += waitingTime.get(process.getName());
        }
        return sum/processes.size();
    }

    @Override
    public double getAverageTurnAroundTime() {
        double turnAroundTime = 0;
        for(Process process : processes){
            turnAroundTime += this.turnAroundTime.get(process.getName());
        }
        return turnAroundTime/processes.size();
    }
}

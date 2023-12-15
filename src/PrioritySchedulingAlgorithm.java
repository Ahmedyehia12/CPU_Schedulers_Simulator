import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class PrioritySchedulingAlgorithm implements SchedulingAlgorithm {
    private PriorityQueue<Process> readyQueue;
    int totalWaitingTime = 0;
    int totalTurnAroundTime = 0;
    private List<Process> processes;
    Map<Process, Integer> startToEnterCPU = new HashMap<>(); // the time when the process entered the CPU

    GUI gui;

    PrioritySchedulingAlgorithm(List<Process> processes, GUI gui) {
        // min heap , the process with the shortest burst time will be at the top
        readyQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority()));
        this.processes = new ArrayList<>();
        for (Process p : processes)
            this.processes.add(new Process(p));
        this.gui = gui;
    }

    void addNewProcesses(int time, Map<String, Integer> added) {
        for (Process process : processes) {
            if (process.getArrivalTime() <= time && !added.containsKey(process.getName())) {
                readyQueue.add(process);
                added.put(process.getName(), 1);
            }
        }
    }

    void decreasePriority(int time) {
        for (Process process : readyQueue) {
            process.setPriority(process.getPriority() - 1);

        }

    }

    @Override
    public void getExecutionOrder() {
        int time = 0;
        readyQueue.clear();
        startToEnterCPU.clear();
        System.out.println("Execution Order : ");

        HashMap<String, Integer> added = new HashMap<>(); // to check if the process is already added to the ready queue
        while (processes.size() != added.size() || !readyQueue.isEmpty()) {
            decreasePriority(time);
            addNewProcesses(time, added);
            if (!readyQueue.isEmpty()) {
                Process p = readyQueue.poll();
                System.out.println(p.getName() + " Entered the CPU at " + time);
                startToEnterCPU.put(p, time);
                gui.addLifeBlock(p, time,time + p.getBurstTime());

                time += p.getBurstTime();
            } else {
                time++;
            }

        }

        System.out.println("\n_________________________________________________________________\n");
    }

    @Override
    public void getWaitingTime() {
        System.out.println("Waiting Time : ");
        for (Process process : processes) {
            int waitingTime = startToEnterCPU.get(process) - process.getArrivalTime();
            System.out.print("Waiting Time for  ");
            System.out.println(process.getName() + " : " + waitingTime);
            totalWaitingTime += waitingTime;
        }
        System.out.println("\n_________________________________________________________________\n");


    }

    @Override
    public void getTurnAroundTime() {
        System.out.println("Turn Around Time : ");
        for (Process process : processes) {
            int turnAroundTime = startToEnterCPU.get(process) + process.getBurstTime() - process.getArrivalTime();
            System.out.print("Turn Around Time for ");
            System.out.println(process.getName() + " : " + turnAroundTime);
            totalTurnAroundTime += turnAroundTime;
        }
        System.out.println("\n_________________________________________________________________\n");


    }

    @Override
    public double getAverageWaitingTime() {
        return totalWaitingTime / processes.size();
    }

    @Override
    public double getAverageTurnAroundTime() {
        return totalTurnAroundTime / processes.size();
    }
}

import java.util.*;

public class AGSchedulingAlgorithm implements SchedulingAlgorithm {
    private Queue<Process> readyQueue;
    private List<Process> processes;
    int totalWaitingTime = 0;
    int totalTurnAroundTime = 0;
    Map<Process, Integer> startToEnterCPU = new HashMap<>(); // the time when the process entered the CPU
    HashMap<Process, Integer> quantum = new HashMap<>();
    int startQuantum;
    HashMap<Process, Integer> waitingTime = new HashMap<>();
    HashMap<Process, Integer> lastTime = new HashMap<>();
    HashMap<Process, Integer> oriBurstTime = new HashMap<>();

    GUI gui;

    AGSchedulingAlgorithm(List<Process> processes, int startQuantum, GUI gui) {
        this.gui = gui;
        readyQueue = new LinkedList<>();
        this.processes = new ArrayList<>();
        for (Process p : processes)
            this.processes.add(new Process(p));
        
        this.startQuantum = startQuantum;
        for (Process p : this.processes) {
            oriBurstTime.put(p, p.getBurstTime());
            quantum.put(p, startQuantum);
        }
    }

    void addNewProcesses(int time, Map<String, Integer> added) {
        for (Process process : processes) {
            if (process.getArrivalTime() <= time && !added.containsKey(process.getName())) {
                readyQueue.add(process);
                added.put(process.getName(), 1);
                lastTime.put(process, process.getArrivalTime());
            }
        }
    }

    public Process minAGFactor() {
        int mn = (int) 1e9;
        Process p = null;
        for (Process tmp : readyQueue) {
            if (mn > tmp.getAGFactor()) {
                p = tmp;
                mn = p.getAGFactor();
            }
        }
        return p;
    }

    public int getNewQuantum() {
        double mean = 0.0;
        for (Process p : quantum.keySet()) {
            mean += quantum.get(p);
        }
        mean = mean / quantum.size();
        mean = mean * 0.1;
        return (int) Math.ceil(mean);
    }

    @Override
    public void getExecutionOrder() {
        int time = 0;
        readyQueue.clear();
        startToEnterCPU.clear();
        System.out.println("Execution Order : ");

        HashMap<String, Integer> added = new HashMap<>();// to check if the process is already added to the ready queue
        Process p = null;
        while (processes.size() != added.size() || !readyQueue.isEmpty() || p != null) {
            addNewProcesses(time, added);
            if (!readyQueue.isEmpty()) {
                if (p == null)
                    p = readyQueue.poll();

                System.out.print("Quantum:");
                int i = 0;
                for (Process tmp : quantum.keySet())
                    System.out.print(
                            " " + tmp.getName() + ": " + quantum.get(tmp) + ",.".charAt(++i == quantum.size() ? 1 : 0));
                System.out.println();

                System.out.println(p.getName() + " Entered the CPU at " + time);
                startToEnterCPU.put(p, time);
                int startTime = time;
                int reqTime = Math.min((int) Math.ceil(quantum.get(p) / ((double) 2)), p.getBurstTime());
                time += reqTime;
                p.reduceBurstTime(reqTime);
                addNewProcesses(time, added);
                while (time - startTime < quantum.get(p)
                        && (readyQueue.isEmpty() || p.getAGFactor() <= minAGFactor().getAGFactor())
                        && p.getBurstTime() > 0) {
                    time++;
                    p.reduceBurstTime(1);
                    addNewProcesses(time, added);
                }

                if (waitingTime.containsKey(p))
                    waitingTime.put(p, startTime - lastTime.get(p) + waitingTime.get(p));
                else
                    waitingTime.put(p, startTime - lastTime.get(p));

                lastTime.put(p, time);
                gui.addLifeBlock(p, startTime, time);
                if (p.getBurstTime() == 0) {
                    quantum.put(p, 0);
                    p = null;
                } else if (time - startTime == quantum.get(p)) {
                    int tmp = quantum.get(p) + getNewQuantum();
                    quantum.put(p, tmp);
                    readyQueue.add(p);
                    p = null;
                } else {
                    int rem = quantum.get(p) - (time - startTime);
                    int tmp = quantum.get(p) + rem;
                    quantum.put(p, tmp);
                    readyQueue.add(p);
                    p = minAGFactor();
                    readyQueue.remove(p);
                }
            } else {
                time++;
            }

        }
        System.out.print("Quantum:");
        int i = 0;
        for (Process tmp : quantum.keySet())
            System.out
                    .print(" " + tmp.getName() + ": " + quantum.get(tmp) + ",.".charAt(++i == quantum.size() ? 1 : 0));
        System.out.println();
        System.out.println("Processes finished at: " + time);
        System.out.println("\n_________________________________________________________________\n");
    }

    @Override
    public void getWaitingTime() {
        System.out.println("Waiting Time : ");
        for (Process p : processes) {
            System.out.println(p.getName() + ' ' + waitingTime.get(p));
            totalWaitingTime += waitingTime.get(p);
        }
        System.out.println("\n_________________________________________________________________\n");
    }

    @Override
    public void getTurnAroundTime() {
        System.out.println("Turn Around Time : ");
        for (Process p : processes) {
            System.out.println(p.getName() + ' ' + (waitingTime.get(p) + oriBurstTime.get(p)));
            totalTurnAroundTime += (waitingTime.get(p) + oriBurstTime.get(p));
        }
        System.out.println("\n_________________________________________________________________\n");
    }

    @Override
    public double getAverageWaitingTime() {
        return (double) totalWaitingTime / processes.size();
    }

    @Override
    public double getAverageTurnAroundTime() {
        return (double) totalTurnAroundTime / processes.size();
    }
}

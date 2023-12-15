import java.util.List;
import java.util.Scanner;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);

        // try{
        //     sc = new Scanner(new File("testcase.txt"));
        // }catch(Exception e){
        //     System.out.println("File not found");
        // }

        List<Process> processes = new ArrayList<>();
        int contextSwitchTime = 1;
        int maxTime = 0;

        System.out.println("Enter the number of processes :");
        int n = sc.nextInt();
        System.out.println("Enter Round Robin Time Quantum :");
        int quantum = sc.nextInt();
        System.out.println("Enter the context switch time :");
        contextSwitchTime = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter the name of the process :");
            String name = sc.next();
            System.out.println("Enter the color of the process :");
            String color = sc.next();
            color = color.toUpperCase();
            System.out.println("Enter the arrival time of the process :");
            int arrivalTime = sc.nextInt();
            System.out.println("Enter the burst time of the process :");
            int burstTime = sc.nextInt();
            System.out.println("Enter the priority of the process :");
            int priority = sc.nextInt();
            processes.add(new Process(name, color, arrivalTime, burstTime, priority));
        }

        // System.setOut(new PrintStream(new BufferedOutputStream((new FileOutputStream("output.txt"))), true));

        // to calculate the max time for the Gannt chart to be displayed
        for (Process process : processes) {
            maxTime += process.getBurstTime() + contextSwitchTime + process.getArrivalTime();
        }
        
        
        GUI SJFgui = new GUI(processes, maxTime, "Shortest Job First Scheduling Algorithm");
        GUI SRTgui = new GUI(processes, maxTime, "Shortest Remaining Time Scheduling Algorithm");
        GUI PSgui = new GUI(processes, maxTime, "Priority Scheduling Algorithm");
        GUI AGgui = new GUI(processes, maxTime, "AG-Scheduling Algorithm");

        SchedulingAlgorithm sjf = new ShortestJobFirst(processes, contextSwitchTime, SJFgui);
        System.out.println("Shortest Job First Scheduling Algorithm");
        sjf.getExecutionOrder();
        sjf.getWaitingTime();
        sjf.getTurnAroundTime();
        System.out.println("Average Waiting Time : " + sjf.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + sjf.getAverageTurnAroundTime() + " ms");
        System.out.println("\n_________________________________________________________________\n");
        SJFgui.addStats(sjf.getAverageWaitingTime(), sjf.getAverageTurnAroundTime());
        SJFgui.show();

        SchedulingAlgorithm srt = new ShortestRemainingTimeFirst(processes, 10, SRTgui);
        System.out.println("Shortest Remaining Time Scheduling Algorithm");
        srt.getExecutionOrder();
        srt.getWaitingTime();
        srt.getTurnAroundTime();
        System.out.println("Average Waiting Time : " + srt.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + srt.getAverageTurnAroundTime() + " ms");
        System.out.println("\n_________________________________________________________________\n");
        SRTgui.addStats(srt.getAverageWaitingTime(), srt.getAverageTurnAroundTime());
        SRTgui.show();

        SchedulingAlgorithm ps = new PrioritySchedulingAlgorithm(processes, PSgui);
        System.out.println("Priority Scheduling Algorithm");
        ps.getExecutionOrder();
        ps.getWaitingTime();
        ps.getTurnAroundTime();
        System.out.println("Average Waiting Time : " + ps.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + ps.getAverageTurnAroundTime() + " ms");
        System.out.println("\n_________________________________________________________________\n");
        PSgui.addStats(ps.getAverageWaitingTime(), ps.getAverageTurnAroundTime());
        PSgui.show();

        SchedulingAlgorithm ag = new AGSchedulingAlgorithm(processes, quantum, AGgui);
        System.out.println("AG-Scheduling Algorithm");
        ag.getExecutionOrder();
        ag.getWaitingTime();
        ag.getTurnAroundTime();
        System.out.println("Average Waiting Time : " + ag.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + ag.getAverageTurnAroundTime() + " ms");
        System.out.println("\n_________________________________________________________________\n");
        AGgui.addStats(ag.getAverageWaitingTime(), ag.getAverageTurnAroundTime());
        AGgui.show();
    }
}
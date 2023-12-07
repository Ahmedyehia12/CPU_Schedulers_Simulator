import java.util.List;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        List<Process> processes =new ArrayList<>();
        processes.add(new Process("P1" , "red" , 0 , 10 , 1));
        processes.add(new Process("P2" , "blue" , 0 , 29 , 2));
        processes.add(new Process("P3" , "green" , 0 , 3 , 3));
        processes.add(new Process("P4" , "yellow" , 0 , 7 , 4));
        processes.add(new Process("P5" , "purple" , 0 , 12 , 5));
        SchedulingAlgorithm sjf = new ShortestJobFirst(processes);
        sjf.getExecutionOrder();
        sjf.getWaitingTime();
        sjf.getTurnAroundTime();
        System.out.println("Average Waiting Time : " + sjf.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + sjf.getAverageTurnAroundTime() + " ms");
    }
}
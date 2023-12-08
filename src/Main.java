import java.util.List;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        List<Process> processes =new ArrayList<>();
        processes.add(new Process("P1" , "red" , 0 , 10 , 1));
        processes.add(new Process("P2" , "blue" , 1 , 2 , 3));
        processes.add(new Process("P3" , "blue" , 4 , 10 , 1));
        processes.add(new Process("P4" , "blue" , 5 , 1 , 1));
        SchedulingAlgorithm sjf = new ShortestRemainingTimeFirst(processes , 0 );
        sjf.getExecutionOrder();
        sjf.getWaitingTime();
        sjf.getTurnAroundTime();
        System.out.println("Average Waiting Time : " + sjf.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + sjf.getAverageTurnAroundTime() + " ms");
    }
}
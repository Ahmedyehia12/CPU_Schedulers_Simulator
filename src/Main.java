import java.util.List;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        List<Process> processes =new ArrayList<>();
        processes.add(new Process("P1" , "red" , 0 , 17 , 4));
        processes.add(new Process("P2" , "blue" , 3 , 6 , 9));
        processes.add(new Process("P3" , "blue" , 4 , 10 , 2));
        processes.add(new Process("P4" , "blue" , 29 , 4 , 8));
        SchedulingAlgorithm sjf = new AGSchedulingAlgorithm(processes , 4);
        sjf.getExecutionOrder();
        sjf.getWaitingTime();
        sjf.getTurnAroundTime();
        System.out.println("Average Waiting Time : " + sjf.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + sjf.getAverageTurnAroundTime() + " ms");
    }
}
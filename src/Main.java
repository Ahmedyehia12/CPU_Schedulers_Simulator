import java.util.List;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        List<Process> processes =new ArrayList<>();
        processes.add(new Process("P1" , "red" , 0 , 1 , 1));
        processes.add(new Process("P2" , "blue" , 2 , 1 , 3));
        processes.add(new Process("P7" , "blue" , 2 , 1 , 1));
        processes.add(new Process("P8" , "blue" , 2 , 1 , 1));
        processes.add(new Process("P3" , "green" , 2 , 2 , 1));
        processes.add(new Process("P4" , "yellow" , 3 , 1 , 2));
        processes.add(new Process("P5" , "purple" , 4 , 1 , 4));
        SchedulingAlgorithm sjf = new PrioritySchedulingAlgorithm(processes );
        sjf.getExecutionOrder();
        sjf.getWaitingTime();
        sjf.getTurnAroundTime();
        System.out.println("Average Waiting Time : " + sjf.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + sjf.getAverageTurnAroundTime() + " ms");
    }
}
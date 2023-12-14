import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        int contextSwitchTime = 1;
        int maxTime = 0;
        processes.add(new Process("P1", "RED", 0, 10, 4));
        processes.add(new Process("P2", "BLUE", 0, 29, 9));
        processes.add(new Process("P3", "YELLOW", 0, 3, 2));
        processes.add(new Process("P4", "CYAN", 0, 7, 8));
        processes.add(new Process("P5", "GREEN", 0, 12, 8));

        // to calculate the max time for the Gannt chart to be displayed
        for (Process process : processes) {
            maxTime += process.getBurstTime()+contextSwitchTime;
        }




        GUI gui = new GUI(processes, maxTime);

        SchedulingAlgorithm sjf = new ShortestRemainingTimeFirst(processes,contextSwitchTime,  gui);
        sjf.getExecutionOrder();
        sjf.getWaitingTime();
        sjf.getTurnAroundTime();

        System.out.println("Average Waiting Time : " + sjf.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + sjf.getAverageTurnAroundTime() + " ms");
        gui.addStats(sjf.getAverageWaitingTime(), sjf.getAverageTurnAroundTime());

        gui.show();
    }
}
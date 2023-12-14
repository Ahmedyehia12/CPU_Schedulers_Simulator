import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        int maxTime = 0;
        processes.add(new Process("P1", "RED", 0, 17, 4));
        processes.add(new Process("P2", "BLUE", 3, 6, 9));
        processes.add(new Process("P3", "YELLOW", 4, 10, 2));
        processes.add(new Process("P4", "CYAN", 29, 4, 8));
        maxTime = 38;
        // for(int i = 0; i < 20; i++){
        //     processes.add(new Process("P" + (i + 5), "RED", i, 17, 4));
        //     maxTime += 17;
        // }

        GUI gui = new GUI(processes, maxTime);

        SchedulingAlgorithm sjf = new ShortestJobFirst(processes,  5,gui);
        sjf.getExecutionOrder();
        sjf.getWaitingTime();
        sjf.getTurnAroundTime();

        System.out.println("Average Waiting Time : " + sjf.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + sjf.getAverageTurnAroundTime() + " ms");
        gui.addStats(sjf.getAverageWaitingTime(), sjf.getAverageTurnAroundTime());

        gui.show();
    }
}
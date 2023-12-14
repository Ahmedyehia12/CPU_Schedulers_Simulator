import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        int maxTime = 0;
        processes.add(new Process("P1", "RED", 0, 17, 4));
        processes.add(new Process("P2", "BLUE", 3, 1, 9));
        processes.add(new Process("P3", "YELLOW", 4, 1, 2));
        processes.add(new Process("P4", "CYAN", 5, 1, 8));
        processes.add(new Process("P5", "BLACK", 6, 1, 8));
        processes.add(new Process("P6", "GREEN", 7, 1, 8));
        processes.add(new Process("P7", "MAGENTA", 8, 1, 8));

        maxTime = 50;
        // for(int i = 0; i < 20; i++){
        //     processes.add(new Process("P" + (i + 5), "RED", i, 17, 4));
        //     maxTime += 17;
        // }

        GUI gui = new GUI(processes, maxTime);

        SchedulingAlgorithm sjf = new ShortestRemainingTimeFirst(processes,  5,gui);
        sjf.getExecutionOrder();
        sjf.getWaitingTime();
        sjf.getTurnAroundTime();

        System.out.println("Average Waiting Time : " + sjf.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + sjf.getAverageTurnAroundTime() + " ms");
        gui.addStats(sjf.getAverageWaitingTime(), sjf.getAverageTurnAroundTime());

        gui.show();
    }
}
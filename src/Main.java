import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        int maxTime = 0;
        processes.add(new Process("P1", "RED", 3, 3, 4));
        processes.add(new Process("P2", "BLUE", 3, 2, 9));
        processes.add(new Process("P3", "YELLOW", 4, 1, 2));
        processes.add(new Process("P4", "CYAN", 10, 4, 8));
        processes.add(new Process("P5", "GREEN", 12, 4, 8));
        processes.add(new Process("P6", "PINK", 12, 4, 8));
        processes.add(new Process("P7", "BLACK", 13, 4, 8));

        maxTime = 50;
        // for(int i = 0; i < 20; i++){
        //     processes.add(new Process("P" + (i + 5), "RED", i, 17, 4));
        //     maxTime += 17;
        // }

        GUI gui = new GUI(processes, maxTime);

        SchedulingAlgorithm sjf = new PrioritySchedulingAlgorithm(processes,  gui);
        sjf.getExecutionOrder();
        sjf.getWaitingTime();
        sjf.getTurnAroundTime();

        System.out.println("Average Waiting Time : " + sjf.getAverageWaitingTime() + " ms");
        System.out.println("Average Turn Around Time : " + sjf.getAverageTurnAroundTime() + " ms");
        gui.addStats(sjf.getAverageWaitingTime(), sjf.getAverageTurnAroundTime());

        gui.show();
    }
}
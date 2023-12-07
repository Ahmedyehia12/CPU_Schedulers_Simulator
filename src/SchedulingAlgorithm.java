public interface SchedulingAlgorithm {
    public void getExecutionOrder();
    public void getWaitingTime();
    public void getTurnAroundTime();
    public int getAverageWaitingTime();
    public int getAverageTurnAroundTime();
}

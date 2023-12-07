import java.util.Random;

public class Process {
    private String name;
    private String color;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int AGFactor;


    public Process(String name, String color, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        Random rand = new Random();
        int rf = rand.nextInt(20);
        if(rf < 10){
            AGFactor = rf + arrivalTime + burstTime;
        }
        else if(rf == 10){
            AGFactor = priority + arrivalTime + burstTime;
        }
        else{
            AGFactor = 10 + arrivalTime + burstTime;
        }
    }
    public int getArrivalTime(){
        return arrivalTime;
    }
    public int getBurstTime(){
        return burstTime;
    }
    public int getPriority(){
        return priority;
    }
    public String getName(){
        return name;
    }
    public String getColor(){
        return color;
    }
    public int getAGFactor(){
        return AGFactor;
    }
}

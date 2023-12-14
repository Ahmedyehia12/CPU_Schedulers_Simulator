import java.util.Random;

public class Process {
    private String name;
    private String color;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int AGFactor;
    static private int idCounter=2023;
    private int id;

    public int getId() {
        return id;
    }



    public Process(String name, String color, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        Random rand = new Random();
        int rf = rand.nextInt(20);
        id = idCounter++;

        if(name == "P1") rf =3;
        else if(name == "P2") rf =8;
        else if(name == "P3") rf =10;
        else if(name == "P4") rf =12;


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
    public void setArrivalTime(int arrivalTime){
        this.arrivalTime = arrivalTime;
    }
    public int getBurstTime(){
        return burstTime;
    }
    public void setBurstTime(int burstTime){
        this.burstTime = burstTime;
    }
    public int getPriority(){
        return priority;
    }
    public void setPriority(int priority){
        this.priority = priority;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }
    public int getAGFactor(){
        return AGFactor;
    }
    public void setAGFactor(int AGFactor){
        this.AGFactor = AGFactor;
    }
    public void reduceBurstTime(int val){ this.burstTime -= val ;}

}

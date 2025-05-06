package oop2;

public class Vehical {

    int wheelsCount;
    String model;

    public Vehical(){
        System.out.println("vehical obj created");
    }

    public Vehical(int wheelsCount){
        this.wheelsCount = wheelsCount;
        System.out.println("vehical obj created with wheelcount");
    }

    void start(){
        System.out.println("vehical starting");
    }

}

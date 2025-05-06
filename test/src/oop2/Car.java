package oop2;

public class Car extends Vehical {

    String color;

    public Car(int whc){
        super(whc);
        // this(x)
        System.out.println("car obj created");
    }

    void start(){
        System.out.println(this);
        System.out.println("car is starting with wheels" + this.wheelsCount);
        super.start();  // previous parent pointing
    }

    public static void main(String[] args) {
        Car obj = new Car(3);
        //obj.wheelsCount = 2;
        obj.model = "i10";
        obj.start();
    }

}

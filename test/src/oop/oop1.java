package oop;

public class oop1 {
    public static void main(String[] args) {
        Dog d1 = new Dog();
        d1.name = "tommy";
        d1.bark();

        Dog d2 = new Dog();
        d2.name = "puppy";
        d2.walk();

        Complex num = new Complex();
        num.a = 2;
        num.b = 4;
        num.print();

    }
}


class Dog{
    String name;
    int age;
    String color;

    void walk(){
        System.out.println( name + " is walking");
    }

    void bark(){
        System.out.println(name + " is barking");
    }
}

class Complex{
    int a ;
    int b;

    void print(){
        System.out.println(a + "+" + b + "i");
    }
}
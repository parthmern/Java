package Arraylist;
import java.util.ArrayList;

public class ArraylistExample {
    public static void main(String[] args) {
        // ArrayList<Type> arrayList = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>();

        // add ele
        numbers.add(1);
        numbers.add(2);
        numbers.add(1);

        System.out.println(numbers); // [1,2,1]

        int index = numbers.get(1);
        System.out.println(index); // 2

        numbers.set(1, 5);
        System.out.println(numbers); // [1,5,1]

        numbers.remove(1);
        System.out.println(numbers);    // [1,1]

        // numbers.size()
    }
}

package basics2;

public class ArrayExplain {

    public static void main(String[] args) {

        // 1. Array allocation
        int[] numbers = new int[5];  // allocates memory for 5 integers

        // 2. Assigning values (indexing starts from 0)
        numbers[0] = 10;
        numbers[1] = 20;
        numbers[2] = 30;
        numbers[3] = 40;
        numbers[4] = 50;

        // 3. Accessing elements
        System.out.println("Accessing specific elements:");
        System.out.println("First element: " + numbers[0]);   // Output: 10
        System.out.println("Last element: " + numbers[4]);    // Output: 50

        // 4. Looping using for loop
        System.out.println("\nLooping with for loop:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("numbers[" + i + "] = " + numbers[i]);
        }
        // Output:
        // numbers[0] = 10
        // numbers[1] = 20 ...

        // 5. Looping using for-each loop
        System.out.println("\nLooping with for-each loop:");
        for (int num : numbers) {
            System.out.println("Value: " + num);
        }
        // Output:
        // Value: 10
        // Value: 20 ...
    }
}

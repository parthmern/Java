package string;

import java.util.Scanner;

public class CharArrayInput {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a word: ");
        String input = sc.nextLine();          // read input as String

        char[] letters = input.toCharArray();  // convert to char array

        // Display the characters
        System.out.println("Characters in array:");
        for (char ch : letters) {
            System.out.println(ch);
        }

        // Optional: Access by index
        System.out.println("First letter: " + letters[0]);
    }
}

package basics2;

public class LoopExamples {

    public static void main(String[] args) {

        // 1. For loop
        System.out.println("For loop:");
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
        // Output:
        // i = 1
        // i = 2
        // i = 3
        // i = 4
        // i = 5

        // 2. While loop
        System.out.println("\nWhile loop:");
        int j = 1;
        while (j <= 3) {
            System.out.println("j = " + j);
            j++;
        }
        // Output:
        // j = 1
        // j = 2
        // j = 3

        // 3. Do-While loop
        System.out.println("\nDo-While loop:");
        int k = 1;
        do {
            System.out.println("k = " + k);
            k++;
        } while (k <= 2);
        // Output:
        // k = 1
        // k = 2

        // 4. Break and Continue
        System.out.println("\nBreak and Continue:");
        for (int m = 1; m <= 5; m++) {
            if (m == 3) {
                continue; // skips when m == 3
            }
            if (m == 5) {
                break; // exits loop when m == 5
            }
            System.out.println("m = " + m);
        }
        // Output:
        // m = 1
        // m = 2
        // m = 4

        // 5. Nested Loops
        System.out.println("\nNested Loops:");
        for (int a = 1; a <= 2; a++) {
            for (int b = 1; b <= 3; b++) {
                System.out.println("a = " + a + ", b = " + b);
            }
        }
        // Output:
        // a = 1, b = 1
        // a = 1, b = 2
        // a = 1, b = 3
        // a = 2, b = 1
        // a = 2, b = 2
        // a = 2, b = 3

        // 6. Labeled Break and Continue
        System.out.println("\nLabeled Break and Continue:");
        outerLoop:
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                if (x == 2 && y == 2) {
                    System.out.println("Break outer at x=2, y=2");
                    break outerLoop; // breaks the outer loop
                }
                System.out.println("x = " + x + ", y = " + y);
            }
        }
        // Output:
        // x = 1, y = 1
        // x = 1, y = 2
        // x = 1, y = 3
        // x = 2, y = 1
        // Break outer at x=2, y=2

        // Labeled continue
        System.out.println("\nLabeled Continue:");
        outer:
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                if (y == 2) {
                    continue outer; // skips to next iteration of outer loop
                }
                System.out.println("x = " + x + ", y = " + y);
            }
        }
        // Output:
        // x = 1, y = 1
        // x = 2, y = 1
        // x = 3, y = 1
    }
}

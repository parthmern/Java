package basics2;

public class ConditionalExamples {

    public static void main(String[] args) {
        int number = 10;

        // 1. Simple if
        if (number > 0) {
            System.out.println("Number is positive");
        }

        // 2. if-else
        if (number % 2 == 0) {
            System.out.println("Number is even");
        } else {
            System.out.println("Number is odd");
        }

        // 3. if-else if-else
        if (number < 0) {
            System.out.println("Negative number");
        } else if (number == 0) {
            System.out.println("Zero");
        } else {
            System.out.println("Positive number");
        }

        // 4. Nested if
        if (number > 0) {
            if (number < 100) {
                System.out.println("Number is between 1 and 99");
            } else {
                System.out.println("Number is 100 or more");
            }
        }

        // 5. switch
        int day = 3;
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            case 7:
                System.out.println("Sunday");
                break;
            default:
                System.out.println("Invalid day");
        }

        // 6. Ternary operator
        String result = (number % 2 == 0) ? "Even" : "Odd";
        System.out.println("Ternary check: Number is " + result);

    }
}


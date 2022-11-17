package net.requef.fizzbuzz;

public class Main {

    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            // Divisible both by 3 and 5.
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("Fizz Buzz");
            }
            // Divisible by 3, but not 5.
            else if (i % 3 == 0) {
                System.out.println("Fizz");
            }
            // Divisible by 5, but not 3
            else if (i % 5 == 0) {
                System.out.println("Buzz");
            }
            // Not divisible by 3 or 5.
            else {
                System.out.println(i);
            }
        }
    }
}

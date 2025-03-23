package me.krishnamurti.concurrency.prime_numbers_example;

import me.krishnamurti.util.MethodTimer;
import me.krishnamurti.util.PrimeUtil;

public class SimplePrimeNumbers {

    private static final int MAX_LIMIT = 100_000_000;


    public static void simplePrimeNumbers(int limit) {
        int primeNumbersCount = 1; // 1 as 2 is already prime number
        int currentNum = 2; // start from 3
        // using while loop for simplicity
        while (currentNum <= limit) {
            currentNum++;
            if (PrimeUtil.checkPrime(currentNum)) {
                primeNumbersCount++;
            }
        }
        System.out.println("primeCount: " + primeNumbersCount);

    }

    public static void main(String[] args) {
        MethodTimer timer = new MethodTimer();
        simplePrimeNumbers(MAX_LIMIT);
        String timerEnd = timer.stop();
        // primeCount: 5761455
        System.out.println(timerEnd); // 1 min 33 seconds
    }

}

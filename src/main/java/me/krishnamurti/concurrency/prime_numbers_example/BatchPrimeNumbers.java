package me.krishnamurti.concurrency.prime_numbers_example;

import me.krishnamurti.util.MethodTimer;
import me.krishnamurti.util.PrimeUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BatchPrimeNumbers {

    private static final int MAX_LIMIT = 100_000_000;
    private static final int NO_OF_THREADS = 10;

    public static void primeNumbersInRange(int threadNo, int start, int limit, AtomicInteger primeNumbersCount) {
        MethodTimer timer = new MethodTimer();

        System.out.println("Thread: " + threadNo + ", start: " + start + ", end: " + limit);

        int currentNum = start < 3 ? 2  : start ; // start from 3
        // using while loop for simplicity
        while (currentNum <= limit) {
            currentNum++;
            if (PrimeUtil.checkPrime(currentNum)) {
                primeNumbersCount.incrementAndGet();
            }
        }

        System.out.println("primeCount: " + primeNumbersCount.get());
        System.out.println("Thread no: " + threadNo + ", time taken: " + timer.stop());

    }


    public static void batchPrimeNumbers(int noOfThreads, int limit) {

        int batchSize = limit / noOfThreads;
        System.out.println("Batch Size: " + batchSize);
        AtomicInteger primeCount = new AtomicInteger();
        ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);
        int start = 3;
        int end = batchSize;
        for (int i = 1; i <= noOfThreads; i++) {
            int finalStart = start;
            int finalEnd = end;
            int finalI = i;
            executor.submit(() -> primeNumbersInRange(finalI, finalStart, finalEnd, primeCount));

            start += batchSize;
            end += batchSize;
        }
        executor.shutdown();

        try {
            boolean isCompleted = executor.awaitTermination(1, TimeUnit.HOURS);
            if (isCompleted) {
                System.out.println("All tasks completed successfully");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Something went wrong");
        }

        System.out.println("total primeCount: " + primeCount.get());
    }

    public static void main(String[] args) {
        MethodTimer timer = new MethodTimer();

        batchPrimeNumbers(NO_OF_THREADS, MAX_LIMIT);

        System.out.println("Total time taken: " + timer.stop());
        // Total time taken: 0 minutes and 21 seconds
    }
}

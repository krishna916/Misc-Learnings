package me.krishnamurti.concurrency.prime_numbers_example;

import me.krishnamurti.util.MethodTimer;
import me.krishnamurti.util.PrimeUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FairThreadPrimeNumbers {
    private static final int MAX_LIMIT = 100_000_000;
    private static final int NO_OF_THREADS = 10;

    public static void primeNumbersInRange( int num, int limit, AtomicInteger primeNumbersCount) {

       // System.out.println("num: " + num);
        if (num<= limit && PrimeUtil.checkPrime(num)) {
            primeNumbersCount.incrementAndGet();
        }
     //   System.out.println( "primeCount: " + primeNumbersCount.get());
//        System.out.println("Thread no: " + threadNo + ", time taken: " + timer.stop());

    }

    public static void fairPrimeNumber(int noOfThreads, int limit) {

        ExecutorService executorService = Executors.newFixedThreadPool(noOfThreads);
        AtomicInteger primeCount = new AtomicInteger(1);
        AtomicInteger currentNum = new AtomicInteger(3);

        CountDownLatch latch = new CountDownLatch(noOfThreads);

        for (int i = 0; i < noOfThreads; i++) {
            executorService.submit(() -> {
                while (true) {
                    int num = currentNum.getAndIncrement();
                    if (num > limit) {
                        break;
                    }

                    primeNumbersInRange(num, limit, primeCount);

                }
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Something went wrong");
        }

        executorService.shutdown();

        try {
            boolean isCompleted = executorService.awaitTermination(1, TimeUnit.HOURS);
            if (isCompleted) {
                System.out.println("All tasks completed successfully, primeCount: " + primeCount.get());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Something went wrong");
        }
    }

    public static void main(String[] args) {
        MethodTimer timer = new MethodTimer();
        fairPrimeNumber(NO_OF_THREADS, MAX_LIMIT);
        System.out.println("Total time taken: " + timer.stop());
        // Total time taken: 0 minutes and 18 seconds
    }
}

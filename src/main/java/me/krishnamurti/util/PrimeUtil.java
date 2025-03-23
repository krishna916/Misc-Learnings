package me.krishnamurti.util;

public class PrimeUtil {
    public static boolean checkPrime(int num) {
        int sqRoot = (int) Math.sqrt(num);
        for (int i = 2; i <= sqRoot; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

}

package me.krishnamurti.util;

import java.time.Duration;
import java.time.Instant;

public class MethodTimer {
    private final Instant start;

    public MethodTimer() {
        this.start = Instant.now();
    }

    public String stop() {
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return String.format("%d minutes and %d seconds", minutes, seconds);
    }
}

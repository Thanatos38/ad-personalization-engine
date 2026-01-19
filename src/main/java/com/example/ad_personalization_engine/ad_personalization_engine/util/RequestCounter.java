package com.example.ad_personalization_engine.ad_personalization_engine.util;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestCounter {

    private static class Counter {
        AtomicInteger count = new AtomicInteger(0);
        long windowStart = Instant.now().getEpochSecond();
    }

    private static final ConcurrentHashMap<String, Counter> counterMap =
            new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS = 10;
    private static final int WINDOW_SECONDS = 60;

    public static synchronized boolean allowRequest(String key) {
        long now = Instant.now().getEpochSecond();

        counterMap.putIfAbsent(key, new Counter());
        Counter counter = counterMap.get(key);

        // Reset window if expired
        if (now - counter.windowStart >= WINDOW_SECONDS) {
            counter.windowStart = now;
            counter.count.set(0);
        }

        return counter.count.incrementAndGet() <= MAX_REQUESTS;
    }
}

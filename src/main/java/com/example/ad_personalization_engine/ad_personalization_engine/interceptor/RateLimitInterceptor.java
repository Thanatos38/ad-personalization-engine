package com.example.ad_personalization_engine.ad_personalization_engine.interceptor;

import com.example.ad_personalization_engine.ad_personalization_engine.config.RateLimitProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final Logger log =
            LoggerFactory.getLogger(RateLimitInterceptor.class);

    private final RateLimitProperties properties;

    private final Map<String, RequestCounter> requestMap =
            new ConcurrentHashMap<>();

    public RateLimitInterceptor(RateLimitProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String ip = request.getRemoteAddr();
        long now = Instant.now().getEpochSecond();

        requestMap.compute(ip, (key, counter) -> {
            if (counter == null ||
                    now - counter.startTime > properties.getWindowSeconds()) {
                return new RequestCounter(1, now);
            }
            counter.count++;
            return counter;
        });

        RequestCounter counter = requestMap.get(ip);

        if (counter.count > properties.getMaxRequests()) {
            log.warn(
                    "RATE_LIMIT_EXCEEDED | ip={} | path={} | count={}",
                    ip, request.getRequestURI(), counter.count
            );
            response.setStatus(429);
            return false;
        }

        return true;
    }

    private static class RequestCounter {
        int count;
        long startTime;

        RequestCounter(int count, long startTime) {
            this.count = count;
            this.startTime = startTime;
        }
    }
}

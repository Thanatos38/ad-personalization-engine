package com.example.ad_personalization_engine.ad_personalization_engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rate.limit")
public class RateLimitProperties {
    private int maxRequests;
    private long windowSeconds;

    public int getMaxRequests(){
        return maxRequests;
    }

    public void setMaxRequests(int maxRequests){
        this.maxRequests = maxRequests;
    }

    public long getWindowSeconds(){
        return windowSeconds;
    }
    public void setWindowSeconds(long windowSeconds){
        this.windowSeconds = windowSeconds;
    }
}

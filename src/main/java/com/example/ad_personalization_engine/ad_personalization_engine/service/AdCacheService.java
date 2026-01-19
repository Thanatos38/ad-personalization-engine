package com.example.ad_personalization_engine.ad_personalization_engine.service;

import com.example.ad_personalization_engine.ad_personalization_engine.dto.AdResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class AdCacheService {
    private final Map<String, AdResponse> cache = new ConcurrentHashMap<>();

    public AdResponse get(String key) {
        return cache.get(key);
    }

    public void put(String key, String adScript) {
        if (adScript == null || adScript.isBlank()) {
            return; // 🚫 never cache null ads
        }
        cache.put(key, new AdResponse(adScript, "CACHE"));
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
    }

}

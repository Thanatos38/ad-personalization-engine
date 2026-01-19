package com.example.ad_personalization_engine.ad_personalization_engine.controller;

import com.example.ad_personalization_engine.ad_personalization_engine.dto.AdRequest;
import com.example.ad_personalization_engine.ad_personalization_engine.dto.AdResponse;
import com.example.ad_personalization_engine.ad_personalization_engine.service.AdCacheService;
import com.example.ad_personalization_engine.ad_personalization_engine.service.BigQueryService;
import com.example.ad_personalization_engine.ad_personalization_engine.service.GeminiService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdGenrationController {

    private final GeminiService geminiService;
    private final AdCacheService adCacheService;
    private final BigQueryService bigQueryService;

    public AdGenrationController(GeminiService geminiService,
                                 AdCacheService adCacheService,
                                 BigQueryService bigQueryService) {
        this.geminiService = geminiService;
        this.adCacheService = adCacheService;
        this.bigQueryService = bigQueryService;
    }

    @PostMapping("/generate-ad")
    public AdResponse generateAd(@Valid @RequestBody AdRequest request) {

        Logger log = LoggerFactory.getLogger(AdGenrationController.class);
        String product = request.getProduct();
        String segment = request.getSegment();
        String description = request.getDescription();

        String cacheKey = product + ";" + segment + ";" + description;
        log.info("AdGenrationController product {} segment {} description {}", product, segment);
        // 1️⃣ Cache
        AdResponse cached = adCacheService.get(cacheKey);
        if (cached != null && cached.getScript() != null && !cached.getScript().isBlank()) {
            return cached;
        }

        // 2️⃣ BigQuery
        String adText = bigQueryService.getAdIfExists(product, segment);
        if (adText != null && !adText.isBlank()) {
            AdResponse response = new AdResponse(adText, "BIGQUERY");
            adCacheService.put(cacheKey, adText);
            return response;
        }

        // 3️⃣ Gemini (CORRECT ORDER)
        adText = geminiService.generateAdScript(product, segment, description);

        // 4️⃣ Persist + Cache
        if (adText != null && !adText.isBlank()) {
            bigQueryService.saveAd(product, segment, adText);
            adCacheService.put(cacheKey, adText);
        }

        return new AdResponse(adText, "GEMINI");
    }
}

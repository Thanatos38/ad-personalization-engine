package com.example.ad_personalization_engine.ad_personalization_engine.controller;

import com.example.ad_personalization_engine.ad_personalization_engine.dto.AudienceSegment;
import com.example.ad_personalization_engine.ad_personalization_engine.dto.SegmentAdResponse;
import com.example.ad_personalization_engine.ad_personalization_engine.service.AdCacheService;
import com.example.ad_personalization_engine.ad_personalization_engine.service.BigQueryService;
import com.example.ad_personalization_engine.ad_personalization_engine.service.GeminiService;
import com.google.cloud.bigquery.BigQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class BulkAdController {
    private final BigQueryService bigQueryService;
    private final GeminiService geminiService;
    private final AdCacheService adCacheService;

    public BulkAdController(BigQueryService bigQueryService, GeminiService geminiService) {
        this.bigQueryService = bigQueryService;
        this.geminiService = geminiService;
        this.adCacheService = new AdCacheService();
    }

    @PostMapping("/generate-all")
    public List<SegmentAdResponse> generateAdsAllSegments(@RequestParam String product)
    {
        List<AudienceSegment> segments = bigQueryService.getAudienceDetails();
        List<SegmentAdResponse> ads = new ArrayList<>();
        for (AudienceSegment segment : segments) {

            String cacheKey = product + ":" + segment.getSegmentId();

            String ad;

            if (adCacheService.contains(cacheKey)) {
                ad = String.valueOf(adCacheService.get(cacheKey));
            } else {
                ad = geminiService.generateAdScript(
                        product,
                        segment.getSegmentId(),
                        segment.getDescription()
                );
                adCacheService.put(cacheKey, ad);
            }

            ads.add(new SegmentAdResponse(segment.getSegmentId(), ad));
        }
        return ads;
    }
}


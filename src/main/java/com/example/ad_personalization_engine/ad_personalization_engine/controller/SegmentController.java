package com.example.ad_personalization_engine.ad_personalization_engine.controller;//package com.example.ad_personalization_engine.ad_personalization_engine.controller;

import com.example.ad_personalization_engine.ad_personalization_engine.service.BigQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SegmentController {
    private final BigQueryService bigQueryService;
    public SegmentController(BigQueryService bigQueryService) {
        this.bigQueryService = bigQueryService;
    }

    @GetMapping("/segments")
    public List<String> getSegments() {
        return bigQueryService.getAudienceSegments();
    }
}



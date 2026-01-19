package com.example.ad_personalization_engine.ad_personalization_engine.dto;

public class AudienceSegment {
    private String segmentId;
    private String description;

    public AudienceSegment(String segmentId, String description) {
        this.segmentId = segmentId;
        this.description = description;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public String getDescription() {
        return description;
    }
}

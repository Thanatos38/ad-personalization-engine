package com.example.ad_personalization_engine.ad_personalization_engine.dto;

public class SegmentAdResponse {
    private String SegmentId;
    private String AdScript;

    public SegmentAdResponse(String SegmentId, String AdScript) {
        this.SegmentId = SegmentId;
        this.AdScript = AdScript;
    }

    public String getSegmentId() {
        return SegmentId;
    }

    public String getAdScript() {
        return AdScript;
    }
}


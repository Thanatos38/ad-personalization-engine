package com.example.ad_personalization_engine.ad_personalization_engine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoResponse {
    private String jobId;
    private String status;

    public VideoResponse(String jobId, String status) {
        this.jobId = jobId;
        this.status = status;

    }
}

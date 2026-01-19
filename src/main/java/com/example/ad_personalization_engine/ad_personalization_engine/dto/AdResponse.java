package com.example.ad_personalization_engine.ad_personalization_engine.dto;

import lombok.Getter;
import lombok.Setter;

public class AdResponse {
    private String adText;
    @Getter
    private String source;

    public AdResponse(String adText, String source) {
        this.adText = adText;
        this.source = source;
    }

    public String getScript() {
        return adText;
    }

    public String getSource() {
        return source;
    }

}

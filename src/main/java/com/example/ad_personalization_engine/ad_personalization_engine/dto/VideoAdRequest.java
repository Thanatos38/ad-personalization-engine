package com.example.ad_personalization_engine.ad_personalization_engine.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoAdRequest {

    @NotBlank
    private String product;

    @NotBlank
    private String segment;

    private String description;



}

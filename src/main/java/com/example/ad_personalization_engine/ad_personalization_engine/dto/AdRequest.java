package com.example.ad_personalization_engine.ad_personalization_engine.dto;

import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor
public class AdRequest {
    @NotBlank(message = "segment is required")
     private String segment;

    @NotBlank(message = "product is required")
     private String product;

    @NotBlank(message = "description is required")
     private String description;

     public String getSegment() {
         return segment;
     }

     public void setSegment(String segment) {
         this.segment = segment;
     }

     public String getProduct() {
         return product;
     }

     public void setProduct(String product) {
         this.product = product;
     }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

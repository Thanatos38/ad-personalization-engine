package com.example.ad_personalization_engine.ad_personalization_engine.service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private static final String PROJECT_ID = "ad-personalization-engine";
    private static final String LOCATION = "us-central1";

    // ✅ Use ONE stable Vertex AI model
    private static final String MODEL = "gemini-2.0-flash";

    public String generateAdScript(
            String product,
            String segmentId,
            String audienceDescription
    ) {

        if (product == null || segmentId == null || audienceDescription == null) {
            throw new IllegalArgumentException(
                    "Product, segment ID, and audience description are mandatory"
            );
        }

        String prompt = String.format("""
            You are a professional marketing copywriter.

            Create a 15-second marketing ad script for the product "%s".

            Target audience: %s
            Audience description: %s

            Requirements:
            - 30–40 spoken words
            - Tone: engaging, modern, persuasive
            - Include a clear call-to-action
            - Do not mention pricing
            """,
                product, segmentId, audienceDescription
        );

        return generateCustomPrompt(prompt);
    }

    public String generateCustomPrompt(String prompt) {

        try (VertexAI vertexAI = new VertexAI(PROJECT_ID, LOCATION)) {

            GenerativeModel model =
                    new GenerativeModel(MODEL, vertexAI);

            GenerateContentResponse response =
                    model.generateContent(prompt); // ✅ TEXT ONLY

            return response.getCandidates(0)
                    .getContent()
                    .getParts(0)
                    .getText();

        } catch (Exception e) {
            throw new RuntimeException("Gemini generation failed", e);
        }
    }
}

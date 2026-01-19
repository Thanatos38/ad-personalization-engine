package com.example.ad_personalization_engine.ad_personalization_engine.dto;

public class VideoScene {

    private int scene;
    private String visual;
    private String voiceOver;
    private int durationSeconds;

    // ✅ REQUIRED by Jackson
    public VideoScene() {
    }

    public VideoScene(int scene, String visual, String voiceOver, int durationSeconds) {
        this.scene = scene;
        this.visual = visual;
        this.voiceOver = voiceOver;
        this.durationSeconds = durationSeconds;
    }

    public int getScene() {
        return scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }

    public String getVisual() {
        return visual;
    }

    public void setVisual(String visual) {
        this.visual = visual;
    }

    public String getVoiceOver() {
        return voiceOver;
    }

    public void setVoiceOver(String voiceOver) {
        this.voiceOver = voiceOver;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    // ✅ YOU WERE MISSING THIS
    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}

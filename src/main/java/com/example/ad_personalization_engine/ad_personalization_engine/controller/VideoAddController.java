package com.example.ad_personalization_engine.ad_personalization_engine.controller;


import com.example.ad_personalization_engine.ad_personalization_engine.dto.VideoAdRequest;
import com.example.ad_personalization_engine.ad_personalization_engine.dto.VideoResponse;
import com.example.ad_personalization_engine.ad_personalization_engine.dto.VideoScene;
import com.example.ad_personalization_engine.ad_personalization_engine.service.VideoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class VideoAddController {

    private final VideoService videoService;

    public VideoAddController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/generate-video-ad")
    public VideoResponse generateVideoAd(
            @RequestBody VideoAdRequest videoAdRequest
            ){
        String jobId = "vid_" + UUID.randomUUID();
        String script = videoService.generateVideoScript(
                videoAdRequest.getProduct(),
                videoAdRequest.getSegment(),
                videoAdRequest.getDescription()
        );

        System.out.println("script : " + script);

        return new VideoResponse(jobId, "Script Generated Successfully");
    }

    @PostMapping("/generate-video-scenes")
    public List<VideoScene> generateVideoScenes(@RequestBody String script) {
        List<VideoScene> scenes = new ArrayList<>();
        System.out.println("Generated scenes: " + scenes);
        return videoService.generateSceneList(script);
    }

}

package com.example.ad_personalization_engine.ad_personalization_engine.service;

import com.example.ad_personalization_engine.ad_personalization_engine.dto.VideoScene;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {
    private final GeminiService geminiService;

    public VideoService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String generateVideoScript(String product, String segment, String description){
        String prompt = String.format(
                """
                Create a 15-second video advertisement script.
                
                Product: %s
                Target audience: %s
                Description: %s
                
                Include:
                - Scene-by-scene breakdown
                - Visual actions
                - Camera cues
                - Background music suggestion
                - Short dialogue or captions
                
                Keep it cinematic and engaging.
                """,
                product,
                segment,
                description == null ? "" : description
        );

        return geminiService.generateCustomPrompt(prompt);
    }

    public List<VideoScene> generateSceneList(String videoScript){
        String prompt = """
        Convert the following video ad script into EXACTLY 5 scenes.

        For each scene return STRICTLY in this format:

        SCENE:<number>
        VISUAL:<what is shown visually>
        VOICE:<spoken narration>
        DURATION:<seconds>

        Rules:
        - No markdown
        - No extra text
        - Duration must total ~15 seconds
        - Keep visuals realistic for AI video generation

        SCRIPT:
        """ + videoScript;

        String aiResponse = geminiService.generateCustomPrompt(prompt);

        return parseScenes(aiResponse);

    }

    private List<VideoScene> parseScenes(String text) {

        List<VideoScene> scenes = new ArrayList<>();

        // Split on SCENE:
        String[] blocks = text.split("SCENE:");

        for (String block : blocks) {
            if (block.isBlank()) continue;

            int scene = 0;
            String visual = "";
            String voice = "";
            int duration = 0;

            // ✅ CORRECT newline split
            String[] lines = block.split("\\n");

            for (String line : lines) {
                line = line.trim();

                if (line.matches("^\\d+.*")) {
                    // Scene number (first line after SCENE:)
                    try {
                        scene = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                    } catch (Exception ignored) {}
                }
                else if (line.startsWith("VISUAL:")) {
                    visual = line.substring("VISUAL:".length()).trim();
                }
                else if (line.startsWith("VOICE:")) {
                    voice = line.substring("VOICE:".length()).trim();
                }
                else if (line.startsWith("DURATION:")) {
                    try {
                        duration = Integer.parseInt(
                                line.substring("DURATION:".length()).trim()
                        );
                    } catch (Exception ignored) {}
                }
            }

            // Only add valid scenes
            if (!visual.isEmpty() || !voice.isEmpty()) {
                scenes.add(new VideoScene(scene, visual, voice, duration));
            }
        }

        return scenes;
    }


}

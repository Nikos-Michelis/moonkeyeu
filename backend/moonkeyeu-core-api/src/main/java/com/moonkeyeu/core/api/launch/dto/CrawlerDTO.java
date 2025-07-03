package com.moonkeyeu.core.api.launch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrawlerDTO {
    private String title;
    private String description;
    private String image;
    private Instant datePublished;
    private Instant dateModified;

    public String getTitle() {
        return title != null && !title.isEmpty()
                ? "MoonkeyEU - " + capitalizeFirstLetter(title)
                : "MoonkeyEU - Space Launch Tracker";
    }

    public String capitalizeFirstLetter(String title) {
        if (title == null || title.isEmpty()) {
            return title;
        }
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }

    public String getDescription(){
        return description != null && !description.isEmpty()
                ? description
                : "Stay up to date with upcoming and past spaceflight from NASA, SpaceX, and other leading space agencies around the world.";
    }


}

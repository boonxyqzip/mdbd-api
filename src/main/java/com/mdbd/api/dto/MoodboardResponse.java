package com.mdbd.api.dto;

import com.mdbd.api.model.Moodboard;
import com.mdbd.api.model.MoodboardItem;
import java.time.LocalDateTime;
import java.util.List;

public class MoodboardResponse {

    private String id;
    private String title;
    private String description;
    private List<MoodboardItem> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MoodboardResponse() {
    }

    public MoodboardResponse(Moodboard moodboard) {
        this.id = moodboard.getId();
        this.title = moodboard.getTitle();
        this.description = moodboard.getDescription();
        this.items = moodboard.getItems();
        this.createdAt = moodboard.getCreatedAt();
        this.updatedAt = moodboard.getUpdatedAt();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<MoodboardItem> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}


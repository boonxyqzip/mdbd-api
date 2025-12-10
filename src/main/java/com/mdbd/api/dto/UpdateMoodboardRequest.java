package com.mdbd.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class UpdateMoodboardRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String description;

    @Valid
    private List<MoodboardItemPayload> items = new ArrayList<>();

    public UpdateMoodboardRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MoodboardItemPayload> getItems() {
        return items;
    }

    public void setItems(List<MoodboardItemPayload> items) {
        this.items = items;
    }
}


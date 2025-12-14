package com.mdbd.api.dto;

import jakarta.validation.constraints.NotBlank;

public class MoodboardItemPayload {

    @NotBlank(message = "아이템 텍스트를 입력해주세요.")
    private String text;
    private String imageUrl;
    private String color;
    private Integer orderIndex;

    public MoodboardItemPayload() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}


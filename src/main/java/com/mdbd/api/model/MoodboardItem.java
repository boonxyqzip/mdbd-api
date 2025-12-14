package com.mdbd.api.model;

public class MoodboardItem {

    private String id;
    private String text;
    private String imageUrl;
    private String color;
    private Integer orderIndex;

    public MoodboardItem() {
    }

    public MoodboardItem(String id, String text, String imageUrl, String color, Integer orderIndex) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.color = color;
        this.orderIndex = orderIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


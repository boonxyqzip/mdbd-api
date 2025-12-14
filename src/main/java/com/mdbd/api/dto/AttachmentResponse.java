package com.mdbd.api.dto;

import com.mdbd.api.model.Attachment;
import java.time.LocalDateTime;

public class AttachmentResponse {

    private String id;
    private String moodboardId;
    private String fileName;
    private String fileUrl;
    private Long fileSize;
    private String contentType;
    private LocalDateTime uploadedAt;

    public AttachmentResponse() {
    }

    public AttachmentResponse(Attachment attachment, String baseUrl) {
        this.id = attachment.getId();
        this.moodboardId = attachment.getMoodboardId();
        this.fileName = attachment.getFileName();
        this.fileUrl = baseUrl + "/api/moodboards/" + attachment.getMoodboardId() + "/attachments/" + attachment.getId() + "/download";
        this.fileSize = attachment.getFileSize();
        this.contentType = attachment.getContentType();
        this.uploadedAt = attachment.getUploadedAt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoodboardId() {
        return moodboardId;
    }

    public void setMoodboardId(String moodboardId) {
        this.moodboardId = moodboardId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}


package com.mdbd.api.service;

import com.mdbd.api.dto.AttachmentResponse;
import com.mdbd.api.model.Attachment;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AttachmentService {

    private final Map<String, Attachment> store = new ConcurrentHashMap<>();
    private final Path uploadDir;

    public AttachmentService(@Value("${app.upload.dir:uploads}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir);
        try {
            if (!Files.exists(this.uploadDir)) {
                Files.createDirectories(this.uploadDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("업로드 디렉토리를 생성할 수 없습니다.", e);
        }
    }

    public AttachmentResponse uploadFile(String moodboardId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파일이 비어있습니다.");
        }

        try {
            String id = UUID.randomUUID().toString();
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String fileName = id + extension;
            Path filePath = uploadDir.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Attachment attachment = new Attachment();
            attachment.setId(id);
            attachment.setMoodboardId(moodboardId);
            attachment.setFileName(originalFileName != null ? originalFileName : "unknown");
            attachment.setFilePath(filePath.toString());
            attachment.setFileSize(file.getSize());
            attachment.setContentType(file.getContentType());
            attachment.setUploadedAt(LocalDateTime.now());

            store.put(id, attachment);
            return new AttachmentResponse(attachment, "http://localhost:8080");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.", e);
        }
    }

    public List<AttachmentResponse> getAttachmentsByMoodboardId(String moodboardId) {
        return store.values().stream()
                .filter(attachment -> moodboardId.equals(attachment.getMoodboardId()))
                .map(attachment -> new AttachmentResponse(attachment, "http://localhost:8080"))
                .sorted((a, b) -> b.getUploadedAt().compareTo(a.getUploadedAt()))
                .collect(Collectors.toList());
    }

    public Attachment getAttachment(String id) {
        Attachment attachment = store.get(id);
        if (attachment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "첨부파일을 찾을 수 없습니다.");
        }
        return attachment;
    }

    public void deleteAttachment(String id) {
        Attachment attachment = store.remove(id);
        if (attachment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "첨부파일을 찾을 수 없습니다.");
        }
        try {
            Files.deleteIfExists(Paths.get(attachment.getFilePath()));
        } catch (IOException e) {
            // 파일 삭제 실패는 무시
        }
    }

    public void deleteAttachmentsByMoodboardId(String moodboardId) {
        List<String> idsToDelete = store.values().stream()
                .filter(attachment -> moodboardId.equals(attachment.getMoodboardId()))
                .map(Attachment::getId)
                .collect(Collectors.toList());
        idsToDelete.forEach(this::deleteAttachment);
    }
}


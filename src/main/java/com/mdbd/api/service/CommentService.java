package com.mdbd.api.service;

import com.mdbd.api.dto.CommentResponse;
import com.mdbd.api.dto.CreateCommentRequest;
import com.mdbd.api.model.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentService {

    private final Map<String, Comment> store = new ConcurrentHashMap<>();

    public CommentResponse createComment(String moodboardId, CreateCommentRequest request) {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        Comment comment = new Comment();
        comment.setId(id);
        comment.setMoodboardId(moodboardId);
        comment.setContent(request.getContent());
        comment.setAuthor(request.getAuthor() != null ? request.getAuthor() : "익명");
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);

        store.put(id, comment);
        return new CommentResponse(comment);
    }

    public List<CommentResponse> getCommentsByMoodboardId(String moodboardId) {
        return store.values().stream()
                .filter(comment -> moodboardId.equals(comment.getMoodboardId()))
                .map(CommentResponse::new)
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public void deleteComment(String id) {
        if (store.remove(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");
        }
    }

    public void deleteCommentsByMoodboardId(String moodboardId) {
        List<String> idsToDelete = store.values().stream()
                .filter(comment -> moodboardId.equals(comment.getMoodboardId()))
                .map(Comment::getId)
                .collect(Collectors.toList());
        idsToDelete.forEach(store::remove);
    }
}


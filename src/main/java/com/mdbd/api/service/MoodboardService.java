package com.mdbd.api.service;

import com.mdbd.api.dto.CreateMoodboardRequest;
import com.mdbd.api.dto.MoodboardItemPayload;
import com.mdbd.api.dto.MoodboardResponse;
import com.mdbd.api.dto.UpdateMoodboardRequest;
import com.mdbd.api.model.Moodboard;
import com.mdbd.api.model.MoodboardItem;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MoodboardService {

    private final Map<String, Moodboard> store = new ConcurrentHashMap<>();

    public MoodboardResponse createMoodboard(CreateMoodboardRequest request) {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        Moodboard moodboard = new Moodboard();
        moodboard.setId(id);
        moodboard.setTitle(request.getTitle());
        moodboard.setDescription(request.getDescription());
        moodboard.setDueDate(request.getDueDate());
        moodboard.setItems(mapItems(request.getItems()));
        moodboard.setCreatedAt(now);
        moodboard.setUpdatedAt(now);

        store.put(id, moodboard);
        return new MoodboardResponse(moodboard);
    }

    public MoodboardResponse updateMoodboard(String id, UpdateMoodboardRequest request) {
        Moodboard existing = getMoodboardOrThrow(id);

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setDueDate(request.getDueDate());
        existing.setItems(mapItems(request.getItems()));
        existing.setUpdatedAt(LocalDateTime.now());

        return new MoodboardResponse(existing);
    }

    public MoodboardResponse getMoodboard(String id) {
        return new MoodboardResponse(getMoodboardOrThrow(id));
    }

    public List<MoodboardResponse> listMoodboards() {
        return store.values().stream()
                .map(MoodboardResponse::new)
                .toList();
    }

    public void deleteMoodboard(String id) {
        if (store.remove(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "무드보드를 찾을 수 없습니다.");
        }
    }

    private Moodboard getMoodboardOrThrow(String id) {
        Moodboard moodboard = store.get(id);
        if (moodboard == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "무드보드를 찾을 수 없습니다.");
        }
        return moodboard;
    }

    private List<MoodboardItem> mapItems(List<MoodboardItemPayload> payloads) {
        if (payloads == null) {
            return new ArrayList<>();
        }

        List<MoodboardItem> items = new ArrayList<>();
        for (int i = 0; i < payloads.size(); i++) {
            MoodboardItemPayload payload = payloads.get(i);
            MoodboardItem item = new MoodboardItem();
            item.setId(UUID.randomUUID().toString());
            item.setText(payload.getText());
            item.setImageUrl(payload.getImageUrl());
            item.setColor(payload.getColor());
            item.setOrderIndex(payload.getOrderIndex() != null ? payload.getOrderIndex() : i);
            items.add(item);
        }
        return items;
    }
}


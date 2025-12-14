package com.mdbd.api.controller;

import com.mdbd.api.dto.CreateMoodboardRequest;
import com.mdbd.api.dto.MoodboardResponse;
import com.mdbd.api.dto.UpdateMoodboardRequest;
import com.mdbd.api.service.MoodboardService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/moodboards")
@CrossOrigin(origins = "*")
public class MoodboardController {

    private final MoodboardService moodboardService;

    public MoodboardController(MoodboardService moodboardService) {
        this.moodboardService = moodboardService;
    }

    @PostMapping
    public ResponseEntity<MoodboardResponse> create(@Valid @RequestBody CreateMoodboardRequest request) {
        MoodboardResponse response = moodboardService.createMoodboard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoodboardResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateMoodboardRequest request
    ) {
        return ResponseEntity.ok(moodboardService.updateMoodboard(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoodboardResponse> get(@PathVariable String id) {
        return ResponseEntity.ok(moodboardService.getMoodboard(id));
    }

    @GetMapping
    public ResponseEntity<List<MoodboardResponse>> list() {
        return ResponseEntity.ok(moodboardService.listMoodboards());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        moodboardService.deleteMoodboard(id);
        return ResponseEntity.noContent().build();
    }
}


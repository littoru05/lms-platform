package com.lms.lms_backend.modules.progress;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.lms_backend.modules.progress.dto.LessonProgressResponse;
import com.lms.lms_backend.modules.progress.dto.UpdateProgressRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    // Cập nhật hoàn thành / chưa hoàn thành bài học
    @PostMapping
    public ResponseEntity<LessonProgressResponse> updateProgress(
            @Valid @RequestBody UpdateProgressRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(progressService.updateLessonProgress(authentication.getName(), request));
    }

    // Lấy danh sách tiến độ các bài học trong khóa học
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LessonProgressResponse>> getCourseProgress(
            @PathVariable Long courseId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(progressService.getCourseProgress(authentication.getName(), courseId));
    }
}
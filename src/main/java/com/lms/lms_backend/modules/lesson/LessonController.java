package com.lms.lms_backend.modules.lesson;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.lms_backend.modules.lesson.dto.LessonRequest;
import com.lms.lms_backend.modules.lesson.dto.LessonResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<LessonResponse>> getLessonsBySection(@PathVariable Long sectionId) {
        return ResponseEntity.ok(lessonService.getLessonsBySection(sectionId));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
    public ResponseEntity<LessonResponse> createLesson(@Valid @RequestBody LessonRequest request) {
        return ResponseEntity.ok(lessonService.createLesson(request));
    }
}
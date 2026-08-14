package com.lms.lms_backend.modules.section;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.lms_backend.modules.section.dto.SectionRequest;
import com.lms.lms_backend.modules.section.dto.SectionResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<SectionResponse>> getSectionsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(sectionService.getSectionsByCourse(courseId));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
    public ResponseEntity<SectionResponse> createSection(@Valid @RequestBody SectionRequest request) {
        return ResponseEntity.ok(sectionService.createSection(request));
    }
}
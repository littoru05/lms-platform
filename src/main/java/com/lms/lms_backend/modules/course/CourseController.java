package com.lms.lms_backend.modules.course;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.lms_backend.modules.course.dto.CourseCreateRequest;
import com.lms.lms_backend.modules.course.dto.CourseResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Xem danh sách khóa học public
    @GetMapping("/public")
    public ResponseEntity<List<CourseResponse>> getPublishedCourses() {
        return ResponseEntity.ok(courseService.getAllPublishedCourses());
    }

    // Xem chi tiết khóa học bằng slug
    @GetMapping("/public/{slug}")
    public ResponseEntity<CourseResponse> getCourseBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(courseService.getCourseBySlug(slug));
    }

    // Chỉ INSTRUCTOR hoặc ADMIN mới được tạo khóa học
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
    public ResponseEntity<CourseResponse> createCourse(
            @Valid @RequestBody CourseCreateRequest request,
            Authentication authentication
    ) {
        String instructorEmail = authentication.getName();
        return ResponseEntity.ok(courseService.createCourse(request, instructorEmail));
    }

    // Xuất bản khóa học
    @PutMapping("/{id}/publish")
    @PreAuthorize("hasAnyAuthority('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
    public ResponseEntity<CourseResponse> publishCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.publishCourse(id));
    }
}
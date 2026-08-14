package com.lms.lms_backend.modules.enrollment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lms.lms_backend.modules.course.dto.CourseResponse;
import com.lms.lms_backend.modules.enrollment.EnrollmentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentResponse {
    private Long id;
    private Long userId;
    private Long courseId;
    private CourseResponse course;
    private BigDecimal progressPercentage;
    private EnrollmentStatus status;
    private LocalDateTime enrolledAt;
}
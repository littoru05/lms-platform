package com.lms.lms_backend.modules.enrollment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequest {
    @NotNull(message = "Course ID không được để trống")
    private Long courseId;
}
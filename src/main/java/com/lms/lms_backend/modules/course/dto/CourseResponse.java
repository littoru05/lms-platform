package com.lms.lms_backend.modules.course.dto;

import com.lms.lms_backend.modules.course.CourseStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CourseResponse {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String thumbnailUrl;
    private BigDecimal price;
    private CourseStatus status;
    private String categoryName;
    private String instructorName;
    private LocalDateTime createdAt;
}
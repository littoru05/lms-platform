package com.lms.lms_backend.modules.course.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseCreateRequest {
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @NotBlank(message = "Slug không được để trống")
    private String slug;

    private String description;
    private String thumbnailUrl;

    @NotNull(message = "Giá tiền không được để trống")
    private BigDecimal price;

    private Long categoryId;
}
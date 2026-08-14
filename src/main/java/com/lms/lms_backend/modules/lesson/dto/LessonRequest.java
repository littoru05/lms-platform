package com.lms.lms_backend.modules.lesson.dto;

import com.lms.lms_backend.modules.lesson.LessonType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LessonRequest {
    @NotNull(message = "Section ID không được để trống")
    private Long sectionId;

    @NotBlank(message = "Tiêu đề bài học không được để trống")
    private String title;

    private LessonType lessonType;
    private String videoUrl;
    private Integer durationSeconds;
    private Integer orderIndex;
    private Boolean isFreePreview;
}
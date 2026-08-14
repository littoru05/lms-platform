package com.lms.lms_backend.modules.lesson.dto;

import com.lms.lms_backend.modules.lesson.LessonType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonResponse {
    private Long id;
    private Long sectionId;
    private String title;
    private LessonType lessonType;
    private String videoUrl;
    private Integer durationSeconds;
    private Integer orderIndex;
    private Boolean isFreePreview;
}
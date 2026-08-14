package com.lms.lms_backend.modules.progress.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonProgressResponse {
    private Long lessonId;
    private Boolean isCompleted;
    private LocalDateTime lastWatchedAt;
    private BigDecimal courseProgressPercentage; // Trả về % tiến độ mới nhất của cả khóa học
}
package com.lms.lms_backend.modules.progress.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProgressRequest {
    @NotNull(message = "Lesson ID không được để trống")
    private Long lessonId;

    @NotNull(message = "Trạng thái hoàn thành không được để trống")
    private Boolean isCompleted;
}
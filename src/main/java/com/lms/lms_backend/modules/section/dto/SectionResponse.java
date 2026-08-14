package com.lms.lms_backend.modules.section.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SectionResponse {
    private Long id;
    private Long courseId;
    private String title;
    private Integer orderIndex;
}
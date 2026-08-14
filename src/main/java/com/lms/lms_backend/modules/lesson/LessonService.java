package com.lms.lms_backend.modules.lesson;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lms.lms_backend.modules.lesson.dto.LessonRequest;
import com.lms.lms_backend.modules.lesson.dto.LessonResponse;
import com.lms.lms_backend.modules.section.Section;
import com.lms.lms_backend.modules.section.SectionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final SectionRepository sectionRepository;

    public List<LessonResponse> getLessonsBySection(Long sectionId) {
        return lessonRepository.findBySectionIdOrderByOrderIndexAsc(sectionId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LessonResponse createLesson(LessonRequest req) {
        Section section = sectionRepository.findById(req.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section không tồn tại!"));

        Lesson lesson = Lesson.builder()
                .title(req.getTitle())
                .lessonType(req.getLessonType() != null ? req.getLessonType() : LessonType.VIDEO)
                .videoUrl(req.getVideoUrl())
                .durationSeconds(req.getDurationSeconds() != null ? req.getDurationSeconds() : 0)
                .orderIndex(req.getOrderIndex() != null ? req.getOrderIndex() : 0)
                .isFreePreview(req.getIsFreePreview() != null ? req.getIsFreePreview() : false)
                .section(section)
                .build();

        return mapToResponse(lessonRepository.save(lesson));
    }

    private LessonResponse mapToResponse(Lesson l) {
        return LessonResponse.builder()
                .id(l.getId())
                .sectionId(l.getSection().getId())
                .title(l.getTitle())
                .lessonType(l.getLessonType())
                .videoUrl(l.getVideoUrl())
                .durationSeconds(l.getDurationSeconds())
                .orderIndex(l.getOrderIndex())
                .isFreePreview(l.getIsFreePreview())
                .build();
    }
}
package com.lms.lms_backend.modules.section;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lms.lms_backend.modules.course.Course;
import com.lms.lms_backend.modules.course.CourseRepository;
import com.lms.lms_backend.modules.section.dto.SectionRequest;
import com.lms.lms_backend.modules.section.dto.SectionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;

    public List<SectionResponse> getSectionsByCourse(Long courseId) {
        return sectionRepository.findByCourseIdOrderByOrderIndexAsc(courseId).stream()
                .map(s -> SectionResponse.builder()
                        .id(s.getId())
                        .courseId(s.getCourse().getId())
                        .title(s.getTitle())
                        .orderIndex(s.getOrderIndex())
                        .build())
                .collect(Collectors.toList());
    }

    public SectionResponse createSection(SectionRequest req) {
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course không tồn tại!"));

        Section section = Section.builder()
                .title(req.getTitle())
                .orderIndex(req.getOrderIndex() != null ? req.getOrderIndex() : 0)
                .course(course)
                .build();

        Section saved = sectionRepository.save(section);
        return SectionResponse.builder()
                .id(saved.getId())
                .courseId(saved.getCourse().getId())
                .title(saved.getTitle())
                .orderIndex(saved.getOrderIndex())
                .build();
    }
}
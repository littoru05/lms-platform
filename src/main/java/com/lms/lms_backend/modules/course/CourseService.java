package com.lms.lms_backend.modules.course;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lms.lms_backend.modules.category.Category;
import com.lms.lms_backend.modules.category.CategoryRepository;
import com.lms.lms_backend.modules.course.dto.CourseCreateRequest;
import com.lms.lms_backend.modules.course.dto.CourseResponse;
import com.lms.lms_backend.modules.user.User;
import com.lms.lms_backend.modules.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<CourseResponse> getAllPublishedCourses() {
        return courseRepository.findByStatus(CourseStatus.PUBLISHED).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse getCourseBySlug(String slug) {
        Course course = courseRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học!"));
        return mapToResponse(course);
    }

    public CourseResponse createCourse(CourseCreateRequest req, String instructorEmail) {
        if (courseRepository.existsBySlug(req.getSlug())) {
            throw new RuntimeException("Slug khóa học đã tồn tại: " + req.getSlug());
        }

        User instructor = userRepository.findByEmail(instructorEmail)
                .orElseThrow(() -> new RuntimeException("Giảng viên không tồn tại với email: " + instructorEmail));

        Category category = null;
        if (req.getCategoryId() != null) {
            category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục (Category) với ID: " + req.getCategoryId()));
        }

        Course course = Course.builder()
                .title(req.getTitle())
                .slug(req.getSlug())
                .description(req.getDescription())
                .thumbnailUrl(req.getThumbnailUrl())
                .price(req.getPrice())
                .status(CourseStatus.DRAFT) // Mặc định tạo mới là DRAFT
                .category(category)
                .instructor(instructor)
                .build();

        Course saved = courseRepository.save(course);
        return mapToResponse(saved);
    }

    public CourseResponse publishCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học!"));
        course.setStatus(CourseStatus.PUBLISHED);
        return mapToResponse(courseRepository.save(course));
    }

    private CourseResponse mapToResponse(Course c) {
        return CourseResponse.builder()
                .id(c.getId())
                .title(c.getTitle())
                .slug(c.getSlug())
                .description(c.getDescription())
                .thumbnailUrl(c.getThumbnailUrl())
                .price(c.getPrice())
                .status(c.getStatus())
                .categoryName(c.getCategory() != null ? c.getCategory().getName() : null)
                .instructorName(c.getInstructor() != null ? c.getInstructor().getFullName() : null)
                .createdAt(c.getCreatedAt())
                .build();
    }
}
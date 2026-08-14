package com.lms.lms_backend.modules.enrollment;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.lms_backend.modules.course.Course;
import com.lms.lms_backend.modules.course.CourseRepository;
import com.lms.lms_backend.modules.course.CourseStatus;
import com.lms.lms_backend.modules.course.dto.CourseResponse;
import com.lms.lms_backend.modules.enrollment.dto.EnrollmentResponse;
import com.lms.lms_backend.modules.user.User;
import com.lms.lms_backend.modules.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Transactional
    public EnrollmentResponse enrollCourse(String userEmail, Long courseId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại!"));

        if (course.getStatus() != CourseStatus.PUBLISHED) {
            throw new RuntimeException("Khóa học chưa được công khai!");
        }

        if (enrollmentRepository.existsByUserIdAndCourseId(user.getId(), course.getId())) {
            throw new RuntimeException("Bạn đã đăng ký khóa học này rồi!");
        }

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .progressPercentage(BigDecimal.ZERO)
                .status(EnrollmentStatus.ACTIVE)
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);
        return mapToResponse(saved);
    }

    public List<EnrollmentResponse> getMyEnrollments(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));

        return enrollmentRepository.findByUserIdOrderByEnrolledAtDesc(user.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public boolean isEnrolled(String userEmail, Long courseId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));
        return enrollmentRepository.existsByUserIdAndCourseId(user.getId(), courseId);
    }

    private EnrollmentResponse mapToResponse(Enrollment e) {
        Course c = e.getCourse();
        CourseResponse courseRes = CourseResponse.builder()
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

        return EnrollmentResponse.builder()
                .id(e.getId())
                .userId(e.getUser().getId())
                .courseId(c.getId())
                .course(courseRes)
                .progressPercentage(e.getProgressPercentage())
                .status(e.getStatus())
                .enrolledAt(e.getEnrolledAt())
                .build();
    }
}
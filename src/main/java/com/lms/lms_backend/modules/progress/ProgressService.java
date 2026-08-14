package com.lms.lms_backend.modules.progress;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.lms_backend.modules.enrollment.Enrollment;
import com.lms.lms_backend.modules.enrollment.EnrollmentRepository;
import com.lms.lms_backend.modules.enrollment.EnrollmentStatus;
import com.lms.lms_backend.modules.lesson.Lesson;
import com.lms.lms_backend.modules.lesson.LessonRepository;
import com.lms.lms_backend.modules.progress.dto.LessonProgressResponse;
import com.lms.lms_backend.modules.progress.dto.UpdateProgressRequest;
import com.lms.lms_backend.modules.user.User;
import com.lms.lms_backend.modules.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final LessonProgressRepository lessonProgressRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public LessonProgressResponse updateLessonProgress(String userEmail, UpdateProgressRequest req) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));

        Lesson lesson = lessonRepository.findById(req.getLessonId())
                .orElseThrow(() -> new RuntimeException("Bài học không tồn tại!"));

        Long courseId = lesson.getSection().getCourse().getId();

        // 1. Kiểm tra xem user đã đăng ký khóa học này chưa
        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(user.getId(), courseId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa đăng ký khóa học này nên không thể lưu tiến độ!"));

        // 2. Cập nhật hoặc tạo mới bản ghi tiến độ bài học
        LessonProgress progress = lessonProgressRepository.findByUserIdAndLessonId(user.getId(), lesson.getId())
                .orElse(LessonProgress.builder()
                        .user(user)
                        .lesson(lesson)
                        .build());

        progress.setIsCompleted(req.getIsCompleted());
        lessonProgressRepository.save(progress);

        // 3. Tự động tính toán lại % hoàn thành của cả khóa học
        long totalLessons = lessonRepository.countByCourseId(courseId);
        long completedLessons = lessonProgressRepository.countCompletedLessonsByUserAndCourse(user.getId(), courseId);

        BigDecimal percentage = BigDecimal.ZERO;
        if (totalLessons > 0) {
            percentage = BigDecimal.valueOf((double) completedLessons / totalLessons * 100)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        // Cập nhật lại vào Enrollment
        enrollment.setProgressPercentage(percentage);
        if (percentage.compareTo(BigDecimal.valueOf(100.00)) >= 0) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
        } else {
            enrollment.setStatus(EnrollmentStatus.ACTIVE);
        }
        enrollmentRepository.save(enrollment);

        return LessonProgressResponse.builder()
                .lessonId(lesson.getId())
                .isCompleted(progress.getIsCompleted())
                .lastWatchedAt(progress.getLastWatchedAt())
                .courseProgressPercentage(percentage)
                .build();
    }

    public List<LessonProgressResponse> getCourseProgress(String userEmail, Long courseId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));

        return lessonProgressRepository.findByUserIdAndCourseId(user.getId(), courseId).stream()
                .map(lp -> LessonProgressResponse.builder()
                        .lessonId(lp.getLesson().getId())
                        .isCompleted(lp.getIsCompleted())
                        .lastWatchedAt(lp.getLastWatchedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
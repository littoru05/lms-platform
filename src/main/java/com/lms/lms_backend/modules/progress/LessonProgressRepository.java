package com.lms.lms_backend.modules.progress;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    Optional<LessonProgress> findByUserIdAndLessonId(Long userId, Long lessonId);

    // Lấy toàn bộ danh sách bài học đã hoàn thành của user trong 1 khóa học
    @Query("SELECT lp FROM LessonProgress lp " +
           "WHERE lp.user.id = :userId " +
           "AND lp.lesson.section.course.id = :courseId")
    List<LessonProgress> findByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);

    // Đếm số bài học đã hoàn thành của user trong khóa học
    @Query("SELECT COUNT(lp) FROM LessonProgress lp " +
           "WHERE lp.user.id = :userId " +
           "AND lp.lesson.section.course.id = :courseId " +
           "AND lp.isCompleted = true")
    long countCompletedLessonsByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
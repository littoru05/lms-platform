package com.lms.lms_backend.modules.course;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findBySlug(String slug);
    List<Course> findByStatus(CourseStatus status);
    List<Course> findByInstructorId(Long instructorId);
    boolean existsBySlug(String slug);
}
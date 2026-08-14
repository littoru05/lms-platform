package com.lms.lms_backend.modules.lesson;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findBySectionIdOrderByOrderIndexAsc(Long sectionId);
}
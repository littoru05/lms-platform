package com.lms.lms_backend.modules.section;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByCourseIdOrderByOrderIndexAsc(Long courseId);
}
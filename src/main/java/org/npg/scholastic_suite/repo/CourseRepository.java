package org.npg.scholastic_suite.repo;

import jakarta.transaction.Transactional;
import org.npg.scholastic_suite.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public interface CourseRepository extends JpaRepository<Course, Long> {
}

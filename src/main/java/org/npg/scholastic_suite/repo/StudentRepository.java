package org.npg.scholastic_suite.repo;

import org.npg.scholastic_suite.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

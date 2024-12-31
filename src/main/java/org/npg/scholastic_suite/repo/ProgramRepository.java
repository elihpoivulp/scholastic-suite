package org.npg.scholastic_suite.repo;

import org.npg.scholastic_suite.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}

package org.npg.scholastic_suite.config;

import org.npg.scholastic_suite.domain.Student;
import org.npg.scholastic_suite.repo.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("development")
@Component
public class DevDBDataInit implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(DevDBDataInit.class);
    private final StudentRepository studentRepository;

    public DevDBDataInit(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) {
        logger.info("Initializing DB..");
        studentRepository.save(new Student("John", "Doe", "john@email.com", "09555555555"));
        studentRepository.save(new Student("Jane", "Doe", "jane@email.com", "+639888888888"));
        logger.info("2 students created.");
    }
}

package org.npg.scholastic_suite.config;

import org.npg.scholastic_suite.domain.Program;
import org.npg.scholastic_suite.domain.Student;
import org.npg.scholastic_suite.repo.ProgramRepository;
import org.npg.scholastic_suite.repo.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DevDBDataInit implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(DevDBDataInit.class);
    private final StudentRepository studentRepository;
    private final ProgramRepository programRepository;

    public DevDBDataInit(StudentRepository studentRepository, ProgramRepository programRepository) {
        this.studentRepository = studentRepository;
        this.programRepository = programRepository;
    }

    @Override
    public void run(String... args) {
        logger.info("Initializing DB..");

        Program program = programRepository.save(new Program("Bachelor of Science in Computer Science", "BSCS"));
        programRepository.save(new Program("Bachelor of Science in Business Administration", "BSBA"));
        logger.info("1 program created");

        studentRepository.save(new Student("John", "Doe", "john@email.com", "09555555555", program));
        studentRepository.save(new Student("Jane", "Doe", "jane@email.com", "+639888888888", program));
        logger.info("2 students created.");
    }
}

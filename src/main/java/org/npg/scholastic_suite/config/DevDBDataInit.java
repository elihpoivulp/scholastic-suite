package org.npg.scholastic_suite.config;

import jakarta.transaction.Transactional;
import org.npg.scholastic_suite.domain.Course;
import org.npg.scholastic_suite.domain.Curriculum;
import org.npg.scholastic_suite.domain.Program;
import org.npg.scholastic_suite.domain.Student;
import org.npg.scholastic_suite.repo.CourseRepository;
import org.npg.scholastic_suite.repo.CurriculumRepository;
import org.npg.scholastic_suite.repo.ProgramRepository;
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
    private final ProgramRepository programRepository;
    private final CourseRepository courseRepository;
    private final CurriculumRepository curriculumRepository;

    public DevDBDataInit(StudentRepository studentRepository, ProgramRepository programRepository, CourseRepository courseRepository, CurriculumRepository curriculumRepository) {
        this.studentRepository = studentRepository;
        this.programRepository = programRepository;
        this.courseRepository = courseRepository;
        this.curriculumRepository = curriculumRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        logger.info("Initializing DB..");

        Program program = programRepository.save(new Program("Bachelor of Science in Computer Science", "BSCS"));
        logger.info("1 program created");

        Curriculum curriculum = curriculumRepository.save(new Curriculum("New BSCS Curriculum"));
        Course prerequisite = courseRepository.save(new Course("Introduction to Programming", "CS101", 3, "Lorem ipsum dolor sit amet", curriculum));
        courseRepository.save(new Course("Thesis 1", "CS52", 5, "Lorem ipsum dolor sit amet", curriculum, prerequisite));
        logger.info("1 curriculum and 2 courses created");

        studentRepository.save(new Student("John", "Doe", "john@email.com", "09555555555", program));
        studentRepository.save(new Student("Jane", "Doe", "jane@email.com", "+639888888888", program));
        logger.info("2 students created.");
    }
}

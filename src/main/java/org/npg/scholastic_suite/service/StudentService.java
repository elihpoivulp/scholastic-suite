package org.npg.scholastic_suite.service;

import jakarta.validation.ConstraintViolationException;
import org.npg.scholastic_suite.constants.ErrorMessages;
import org.npg.scholastic_suite.domain.Program;
import org.npg.scholastic_suite.domain.Student;
import org.npg.scholastic_suite.repo.ProgramRepository;
import org.npg.scholastic_suite.repo.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final ProgramRepository programRepository;

    public StudentService(StudentRepository studentRepository, ProgramRepository programRepository) {
        this.studentRepository = studentRepository;
        this.programRepository = programRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException(ErrorMessages.DOESNT_EXIST));
    }

    public Student createStudent(Student student) {
        // check first if program exists
        Program program = programRepository.findById(student.getProgram().getId()).orElseThrow(() -> new NoSuchElementException(ErrorMessages.DOESNT_EXIST));
        student.setProgram(program);
        return studentRepository.save(student);
    }

    public Student updateStudent(long id, Student student) throws Throwable {
        // check first if student exists
        Student fetchedStudent = studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException(ErrorMessages.DOESNT_EXIST));

        fetchedStudent.setFirstName(student.getFirstName());
        fetchedStudent.setLastName(student.getLastName());
        fetchedStudent.setEmail(student.getEmail());
        fetchedStudent.setPhone(student.getPhone());
        try {
            return studentRepository.save(fetchedStudent);
        } catch (TransactionSystemException e) {
            if (e.getCause().getCause() instanceof ConstraintViolationException) {
                throw e.getCause().getCause();
            }
            throw e;
        }
    }

    public void deleteStudent(long id) {
        try {
            this.getStudentById(id);
            studentRepository.deleteById(id);
            logger.warn("Student with id {} has been deleted.", id);
        } catch (NoSuchElementException e) {
            logger.warn(ErrorMessages.ATTEMPT_FAILED, "student", id);
        }
    }
}

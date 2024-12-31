package org.npg.scholastic_suite.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.npg.scholastic_suite.constants.ErrorMessages;
import org.npg.scholastic_suite.domain.Student;
import org.npg.scholastic_suite.util.TestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@EnableTransactionManagement
public class StudentsServiceIntegrationTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void testGetAllStudentsShouldReturnAllStudents() {
        List<Student> students = studentService.getAllStudents();

        Student student1 = students.getFirst();
        Student student2 = students.getLast();

        assertEquals(2, students.size());
        assertEquals("John", student1.getFirstName());
        assertEquals("Jane", student2.getFirstName());
    }

    @Test
    public void testGetStudentByIdShouldReturnStudent() {
        Student student = studentService.getStudentById(1);
        assertNotNull(student);
        assertEquals(1L, student.getId());
        assertEquals("John", student.getFirstName());
    }

    @Test
    public void testNonExistentGetStudentByIdShouldReturnNoSuchElementException() {
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> {
            studentService.getStudentById(999999L);
        });
        assertEquals(ErrorMessages.DOESNT_EXIST, ex.getMessage());
    }

    @Test
    @DirtiesContext
    public void testCreateStudentShouldReturnStudent() {
        Student student = TestHelper.generateStudent(false);
        Student savedStudent = studentService.createStudent(student);
        assertNotNull(savedStudent);
        assertEquals(3L, savedStudent.getId());
    }

    @Test
    public void testCreateStudentWithInvalidConstraintsShouldReturnConstraintViolationException() {
        Student student = TestHelper.generateStudent(false);
        student.setEmail("invalid-email");
        ConstraintViolationException ex1 = assertThrows(ConstraintViolationException.class, () -> {
            studentService.createStudent(student);
        });
        for(ConstraintViolation<?> cv : ex1.getConstraintViolations()) {
            assertEquals("email", cv.getPropertyPath().toString());
        }

        student.setEmail("valid@email.com");
        student.setPhone("invalid-phone");
        ConstraintViolationException ex2 = assertThrows(ConstraintViolationException.class, () -> {
            studentService.createStudent(student);
        });
        for(ConstraintViolation<?> cv : ex2.getConstraintViolations()) {
            assertEquals("phone", cv.getPropertyPath().toString());
        }

        student.setEmail("invalid-email");
        student.setPhone("invalid-phone");
        ConstraintViolationException ex3 = assertThrows(ConstraintViolationException.class, () -> {
            studentService.createStudent(student);
        });
        for(ConstraintViolation<?> cv : ex3.getConstraintViolations()) {
            assertThat(cv.getPropertyPath().toString(), anyOf(is("email"), is("phone")));
        }
    }

    @Test
    @DirtiesContext
    public void testUpdateStudentShouldReturnStudent() throws Throwable {
        Student student = TestHelper.generateStudent(false);
        student.setFirstName("updatedFirstName");
        Student updatedStudent = studentService.updateStudent(1L, student);
        assertNotNull(updatedStudent);
        assertEquals("updatedFirstName", updatedStudent.getFirstName());
    }

    @Test
    public void testUpdateStudentWithInvalidConstraintsShouldReturnConstraintViolationException() {
        Student student = TestHelper.generateStudent(false);
        student.setEmail("invalid-email");

        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
            studentService.updateStudent(1L, student);
        });
        for(ConstraintViolation<?> cv : ex.getConstraintViolations()) {
            assertEquals("email", cv.getPropertyPath().toString());
        }
    }

    @Test
    public void testNonExistentUpdateStudentShouldReturnNoSuchElementException() {
        Student student = TestHelper.generateStudent(false);
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> {
            studentService.updateStudent(99999999L, student);
        });
        assertEquals(ErrorMessages.DOESNT_EXIST, ex.getMessage());
    }

    @Test
    @DirtiesContext
    public void testDeleteStudentShouldDeleteStudent() {
        studentService.deleteStudent(1L);

        assertThrows(NoSuchElementException.class, () -> {
            studentService.getStudentById(1L);
        });
    }
}
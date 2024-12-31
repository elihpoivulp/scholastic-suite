package org.npg.scholastic_suite.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.npg.scholastic_suite.constants.ErrorMessages;
import org.npg.scholastic_suite.domain.Student;
import org.npg.scholastic_suite.repo.StudentRepository;
import org.npg.scholastic_suite.util.TestHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentsServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        TestHelper.resetCounter();
    }

    @Test
    public void testGetAllStudentsShouldReturnAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(TestHelper.generateStudent());
        students.add(TestHelper.generateStudent());

        when(studentService.getAllStudents()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        Student student1 = result.getFirst();
        Student student2 = result.getLast();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, student1.getId());
        assertEquals("Juan_1", student1.getFirstName());
        assertEquals(2L, student2.getId());
        assertEquals("Juan_2", student2.getFirstName());

        verify(studentRepository).findAll();
    }

    @Test
    public void testGetStudentByIdShouldReturnStudent() {
        Student mockStudent = TestHelper.generateStudent();
        when(studentRepository.findById(mockStudent.getId())).thenReturn(Optional.of(mockStudent));

        Student student = studentService.getStudentById(mockStudent.getId());

        assertNotNull(student);
        assertEquals(mockStudent.getId(), student.getId());
        assertEquals("Juan_1", student.getFirstName());

        verify(studentRepository).findById(anyLong());
    }

    @Test
    public void testNonExistentGetStudentByIdShouldThrowNoSuchElementException() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> {
            studentService.getStudentById(anyLong());
        });

        assertEquals(ErrorMessages.DOESNT_EXIST, ex.getMessage());
        verify(studentRepository).findById(anyLong());
    }

    @Test
    public void testCreateStudentShouldReturnStudent() {
        Student mockStudent = TestHelper.generateStudent(false);
        when(studentService.createStudent(mockStudent)).thenReturn(mockStudent);
        studentService.createStudent(mockStudent);
        verify(studentRepository).save(mockStudent);
    }

    @Test
    public void testUpdateStudentShouldUpdateStudent() throws Throwable {
        Student mockStudent = TestHelper.generateStudent(true);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mockStudent));
        studentService.updateStudent(1, mockStudent);
        verify(studentRepository).findById(anyLong());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    public void testNonExistentUpdateStudentShouldThrowNoSuchElementException() {
        Student mockStudent = TestHelper.generateStudent(true);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> { studentService.updateStudent(1L, mockStudent); });
        assertEquals(ErrorMessages.DOESNT_EXIST, ex.getMessage());
        verify(studentRepository).findById(anyLong());
    }

    @Test
    public void testDeleteStudentShouldDeleteStudent() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(TestHelper.generateStudent()));
        studentService.deleteStudent(1);
        verify(studentRepository).deleteById(anyLong());
    }

    @Test
    public void testNonExistentDeleteStudentShouldNoNothing() {
        studentService.deleteStudent(1);
        verify(studentRepository, never()).deleteById(anyLong());
    }
}

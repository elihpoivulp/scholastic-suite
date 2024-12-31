package org.npg.scholastic_suite.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.npg.scholastic_suite.constants.ErrorMessages;
import org.npg.scholastic_suite.domain.Program;
import org.npg.scholastic_suite.repo.ProgramRepository;
import org.npg.scholastic_suite.util.TestHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceUnitTest {
    @Mock
    private ProgramRepository programRepository;

    @InjectMocks
    private ProgramService programService;

    @BeforeEach
    void setUp() {
        TestHelper.resetCounter();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void when_getAllPrograms_then_return_program_list() {
        List<Program> programs = new ArrayList<>();
        programs.add(TestHelper.generateProgram());
        programs.add(TestHelper.generateProgram());

        when(programService.getAllPrograms()).thenReturn(programs);

        List<Program> result = (List<Program>) TestHelper.toList(programService.getAllPrograms());

        Program program1 = result.getFirst();
        Program program2 = result.getLast();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, program1.getId());
        assertEquals("Bachelor of Science in Computer Science_1", program1.getName());
        assertEquals("BSCS_1", program1.getCode());
        assertEquals(2L, program2.getId());
        assertEquals("Bachelor of Science in Computer Science_2", program2.getName());
        assertEquals("BSCS_2", program2.getCode());

        verify(programRepository).findAll();
    }

    @Test
    public void when_getProgramById_then_return_program() {
        Program mockProgram = TestHelper.generateProgram();
        when(programRepository.findById(mockProgram.getId())).thenReturn(Optional.of(mockProgram));

        Program student = programService.getProgramById(mockProgram.getId());

        assertNotNull(student);
        assertEquals(mockProgram.getId(), student.getId());
        assertEquals("BSCS_1", student.getCode());

        verify(programRepository).findById(anyLong());
    }

    @Test
    public void when_getProgramById_but_program_doesnt_exist_then_throw_NoSuchElementException() {
        when(programRepository.findById(anyLong())).thenReturn(Optional.empty());
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> {
            programService.getProgramById(anyLong());
        });

        assertEquals(ErrorMessages.DOESNT_EXIST, ex.getMessage());
        verify(programRepository).findById(anyLong());
    }
}

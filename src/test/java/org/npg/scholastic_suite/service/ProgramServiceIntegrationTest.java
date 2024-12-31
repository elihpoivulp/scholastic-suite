package org.npg.scholastic_suite.service;

import org.junit.jupiter.api.Test;
import org.npg.scholastic_suite.constants.ErrorMessages;
import org.npg.scholastic_suite.domain.Program;
import org.npg.scholastic_suite.util.TestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
public class ProgramServiceIntegrationTest {
    @Autowired
    private ProgramService programService;

    @Test
    @SuppressWarnings("unchecked")
    public void when_getAllPrograms_then_return_program_list() {
        List<Program> result = (List<Program>) TestHelper.toList(programService.getAllPrograms());

        Program program1 = result.getFirst();
        Program program2 = result.getLast();

        assertEquals(2, result.size());
        assertEquals("BSCS", program1.getCode());
        assertEquals("BSBA", program2.getCode());
    }

    @Test
    public void when_getProgramById_then_return_student() {
        Program program = programService.getProgramById(1);
        assertNotNull(program);
        assertEquals(1L, program.getId());
        assertEquals("BSCS", program.getCode());
    }

    @Test
    public void when_getProgramById_but_student_doesnt_exist_then_throw_NoSuchElementException() {
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> {
            programService.getProgramById(999999L);
        });
        assertEquals(ErrorMessages.DOESNT_EXIST, ex.getMessage());
    }
}

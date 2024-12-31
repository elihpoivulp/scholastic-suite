package org.npg.scholastic_suite.service;

import org.npg.scholastic_suite.constants.ErrorMessages;
import org.npg.scholastic_suite.domain.Program;
import org.npg.scholastic_suite.repo.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProgramService {
    private final ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public Iterable<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    public Program getProgramById(long id) {
        return programRepository.findById(id).orElseThrow(() -> new NoSuchElementException(ErrorMessages.DOESNT_EXIST));
    }
}

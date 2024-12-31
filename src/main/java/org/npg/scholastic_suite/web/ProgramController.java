package org.npg.scholastic_suite.web;

import org.npg.scholastic_suite.domain.Program;
import org.npg.scholastic_suite.service.ProgramService;
import org.npg.scholastic_suite.web.assembler.ProgramAssembler;
import org.npg.scholastic_suite.web.assembler.StudentAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/programs")
public class ProgramController {
    private final ProgramService programService;
    private final ProgramAssembler programAssembler;
    private final StudentAssembler studentAssembler;

    public ProgramController(ProgramService programService, ProgramAssembler programAssembler, StudentAssembler studentAssembler) {
        this.programService = programService;
        this.programAssembler = programAssembler;
        this.studentAssembler = studentAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Program>>> getAllPrograms() {
        return ResponseEntity.ok(programAssembler.toCollectionModel(programService.getAllPrograms()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Program>> getProgramById(@PathVariable long id) {
        return ResponseEntity.ok(programAssembler.toModel(Objects.requireNonNull(programService.getProgramById(id))));
    }

    // student
    @GetMapping("/{id}/students")
    public ResponseEntity<?> getProgramStudents(@PathVariable long id) {
        return ResponseEntity.ok().body(studentAssembler.toCollectionModel(programService.getProgramById(id).getStudents()));
    }
}

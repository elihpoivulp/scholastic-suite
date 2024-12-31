package org.npg.scholastic_suite.web;

import org.npg.scholastic_suite.domain.Student;
import org.npg.scholastic_suite.service.StudentService;
import org.npg.scholastic_suite.web.assembler.StudentAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentAssembler studentAssembler;

    public StudentController(StudentService studentService, StudentAssembler studentAssembler) {
        this.studentService = studentService;
        this.studentAssembler = studentAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Student>>> getAllStudents() {
        return ResponseEntity.ok(studentAssembler.toCollectionModel(studentService.getAllStudents()));
    }

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return ResponseEntity.created(studentAssembler.toModel(newStudent).getRequiredLink(IanaLinkRelations.SELF).toUri()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Student>> getOneStudentById(@PathVariable long id) {
        return ResponseEntity.ok(studentAssembler.toModel(studentService.getStudentById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable long id, @RequestBody Student student) throws Throwable {
        studentService.updateStudent(id, student);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}

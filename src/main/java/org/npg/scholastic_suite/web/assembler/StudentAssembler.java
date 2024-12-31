package org.npg.scholastic_suite.web.assembler;

import jakarta.annotation.Nonnull;
import org.npg.scholastic_suite.domain.Student;
import org.npg.scholastic_suite.web.StudentController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudentAssembler implements RepresentationModelAssembler<Student, EntityModel<Student>> {
    @Override
    @Nonnull
    public EntityModel<Student> toModel(@Nonnull Student entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(StudentController.class).getStudentById(entity.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).getStudentProgram(entity.getId())).withRel("program"),
                linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students")
        );
    }

    @Override
    @Nonnull
    public CollectionModel<EntityModel<Student>> toCollectionModel(@Nonnull Iterable<? extends Student> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
        // CollectionModel<EntityModel<Student>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);
        // CollectionModel<EntityModel<Student>> list = CollectionModel.of(collectionModel.getContent());
        // list.add(collectionModel.getLinks());
        // list.add(Link.of(linkTo(StudentController.class).toUri().toString(), "self"));
        // return list;
    }
}

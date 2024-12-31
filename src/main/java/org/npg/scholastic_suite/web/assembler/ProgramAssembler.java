package org.npg.scholastic_suite.web.assembler;

import jakarta.annotation.Nonnull;
import org.npg.scholastic_suite.domain.Program;
import org.npg.scholastic_suite.web.ProgramController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProgramAssembler implements RepresentationModelAssembler<Program, EntityModel<Program>> {
    @Override
    @Nonnull
    public EntityModel<Program> toModel(@Nonnull Program entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ProgramController.class).getProgramById(entity.getId())).withSelfRel(),
                linkTo(methodOn(ProgramController.class).getProgramStudents(entity.getId())).withRel("students"),
                linkTo(methodOn(ProgramController.class).getAllPrograms()).withRel("programs")
        );
    }

    @Override
    @Nonnull
    public CollectionModel<EntityModel<Program>> toCollectionModel(@Nonnull Iterable<? extends Program> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
        // CollectionModel<EntityModel<Program>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);
        // CollectionModel<EntityModel<Program>> list = CollectionModel.of(collectionModel.getContent());
        // list.add(collectionModel.getLinks());
        // list.add(Link.of(linkTo(ProgramController.class).toUri().toString(), "self"));
        // return list;
    }
}

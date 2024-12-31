package org.npg.scholastic_suite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.npg.scholastic_suite.constants.ErrorMessages;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Relation(collectionRelation = "programs")
public class Program extends BaseEntity {
    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Size(min = 2, max = 50, message = ErrorMessages.GLOBAL_CHAR_SIZE_RANGE)
    private String name;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Size(min = 2, max = 50, message = ErrorMessages.GLOBAL_CHAR_SIZE_RANGE)
    private String code;

    @JsonIgnore
    @OneToMany(mappedBy = "program")
    private List<Student> students;

    public Program(final String name, final String code) {
        setName(name);
        setCode(code);
    }
}

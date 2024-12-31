package org.npg.scholastic_suite.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.npg.scholastic_suite.constants.CurriculumType;
import org.npg.scholastic_suite.constants.ErrorMessages;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name = "curriculum_type_idx", columnList = "type")})
public class Curriculum extends BaseEntity {
    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Size(min = 2, max = 50, message = ErrorMessages.GLOBAL_CHAR_SIZE_RANGE)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private CurriculumType type = CurriculumType.OFFICIAL;

    @OneToMany(mappedBy = "curriculum")
    private List<Course> courses;

    public Curriculum(String name) {
        setName(name);
    }

    public Curriculum(String name, CurriculumType type) {
        setName(name);
        setType(type);
    }
}

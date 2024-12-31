package org.npg.scholastic_suite.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.npg.scholastic_suite.constants.ErrorMessages;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course extends BaseEntity {
    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Size(min = 2, max = 50, message = ErrorMessages.GLOBAL_CHAR_SIZE_RANGE)
    private String name;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Size(min = 2, max = 50, message = ErrorMessages.GLOBAL_CHAR_SIZE_RANGE)
    private String code;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Size(min = 2, max = 500, message = "Field cannot exceed 500 characters.")
    private String description;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Min(value = 1, message = ErrorMessages.CREDITS_RANGE)
    @Max(value = 5, message = ErrorMessages.CREDITS_RANGE)
    private Integer credits;

    @ManyToOne
    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Curriculum curriculum;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JoinColumn(name = "prerequisite")
    private Course prerequisite;

    public Course(String name, String code, int credits, String description, Curriculum curriculum) {
        setName(name);
        setCode(code);
        setCredits(credits);
        setDescription(description);
        setCurriculum(curriculum);
    }

    public Course(String name, String code, int credits, String description, Curriculum curriculum, Course prerequisite) {
        setName(name);
        setCode(code);
        setCredits(credits);
        setDescription(description);
        setCurriculum(curriculum);
        setPrerequisite(prerequisite);
    }
}

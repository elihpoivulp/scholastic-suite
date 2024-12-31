package org.npg.scholastic_suite.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.npg.scholastic_suite.constants.ErrorMessages;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Relation(collectionRelation = "students")
public class Student extends PersonEntity {
    @Pattern(regexp = "^(?:\\+63|0)9\\d{9}$", message = ErrorMessages.INVALID_PHONE)
    private String phone;

    @ManyToOne
    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Program program;

    public Student(String firstName, String lastName, String email, String phone) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhone(phone);
    }

    public Student(String firstName, String lastName, String email, String phone, Program program) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhone(phone);
        setProgram(program);
    }
}

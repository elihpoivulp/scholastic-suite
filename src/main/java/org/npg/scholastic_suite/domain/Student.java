package org.npg.scholastic_suite.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.npg.scholastic_suite.constants.ErrorMessages;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Student extends PersonEntity {
    @Pattern(regexp = "^(?:\\+63|0)9\\d{9}$", message = ErrorMessages.INVALID_PHONE)
    private String phone;

    public Student(String firstName, String lastName, String email, String phone) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhone(phone);
    }
}

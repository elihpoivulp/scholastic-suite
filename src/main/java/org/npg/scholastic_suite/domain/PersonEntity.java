package org.npg.scholastic_suite.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.npg.scholastic_suite.constants.ErrorMessages;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity extends BaseEntity {
    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Size(min = 2, max = 30, message = ErrorMessages.GLOBAL_CHAR_SIZE_RANGE)
    private String firstName;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Size(min = 2, max = 30, message = ErrorMessages.GLOBAL_CHAR_SIZE_RANGE)
    private String lastName;

    @Email(message = ErrorMessages.INVALID_EMAIL)
    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Size(min = 6, max = 50, message = ErrorMessages.EMAIL_CHAR_SIZE_RANGE)
    @Column(unique = true)
    private String email;
}

package com.store.book.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * UserCreateDTO
 * DTO for User creation and login
 *
 * @author Amit Vs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}

package com.store.book.domain;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

/**
 * UserCreateDTO
 * DTO for User creation and login
 *
 * @author Amit Vs
 */
@Builder
@Getter
public class UserDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}

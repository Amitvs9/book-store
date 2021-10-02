package com.store.book.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Amit Vs
 */
@Builder
@Getter
public class UserResponse {

    private String token;
    private UserDetails userDetails;
}

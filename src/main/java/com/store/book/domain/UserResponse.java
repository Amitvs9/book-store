package com.store.book.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Amit Vs
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserResponse {

    private String token;
    private UserDetails userDetails;
}

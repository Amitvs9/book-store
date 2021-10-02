package com.store.book.service;

import com.store.book.domain.UserResponse;
import com.store.book.entities.User;
import com.store.book.error.BookStoreErrorConstants;
import com.store.book.error.BookStoreException;
import com.store.book.repository.UserRepository;
import com.store.book.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

/**
 * UserService
 * Service containing user related business logic
 *
 * @author Amit Vs
 */
@Slf4j
@Component
public class UserService {

    @Value("${jwt.secret}")
    private String authenticationSigningSecret;

    private final UserDetailsServiceImp userDetailsServiceImp;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserDetailsServiceImp userDetailsServiceImp, UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(@Valid User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        getByUsername(user).
                        ifPresent(existingUser -> {
                            log.warn("Can not create user. already exist : {}", user.getUsername());
                            throw new BookStoreException(BookStoreErrorConstants.ERROR_USER_EXIST, BookStoreErrorConstants.USER_ALREADY_EXIST);
                        });

       return userRepository.save(user);
    }

    public Optional<User> getByUsername(User user) {
        return userRepository.findByUsername(user.getUsername());
    }


    public UserResponse signInUserAndGenerateJWT(@Valid User user) {
        authenticateUser(user);
        final UserDetails userDetails = getUserDetails(user.getUsername());
        if(userDetails==null){
            log.error("User login attempt failed for {} ", user.getUsername());
            throw new BookStoreException(BookStoreErrorConstants.ERROR_BAD_CREDENTIALS_E1001, BookStoreErrorConstants.BAD_CREDENTIALS);

        }
        return UserResponse.builder()
                        .token(AuthUtils.generateJWT(userDetails.getUsername(), authenticationSigningSecret))
                        .userDetails(userDetails).build();
    }


    private void authenticateUser(User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        }catch (DisabledException | BadCredentialsException ex) {
            log.error("User login attempt failed for {} reason : {}", user.getUsername(), ex.getLocalizedMessage());
            throw new BookStoreException(BookStoreErrorConstants.ERROR_BAD_CREDENTIALS_E1001, BookStoreErrorConstants.BAD_CREDENTIALS);
        }catch (AuthenticationException aex){
            log.error("User login attempt failed for {} reason : {}", user.getUsername(), aex.getLocalizedMessage());
            throw new BookStoreException(BookStoreErrorConstants.ERROR_BAD_CREDENTIALS_E1001, BookStoreErrorConstants.BAD_CREDENTIALS);
        }
    }

    public UserDetails loggedInUser(Principal principal) {
        return getUserDetails(principal.getName());
    }

    private UserDetails getUserDetails(String username) {
        return userDetailsServiceImp.loadUserByUsername(username);

    }

}

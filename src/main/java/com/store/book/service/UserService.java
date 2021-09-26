package com.store.book.service;

import com.store.book.entities.User;
import com.store.book.repository.UserRepository;
import com.store.book.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * UserService
 * Service containing user related business logic
 */
@Slf4j
@Component
public class UserService {

    @Value("${jwt.secret}")
    private String authenticationSigningSecret;

    private final UserRepository userRepo;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepo, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createUser(@Valid User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.findById(user.getUserName()).
                        ifPresent(existingUser -> {
                            log.warn("Can not create user. already exist : {}", user.getUserName());
                            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "RecipeAppConstants.USER_ALREADY_EXIST");
                        });

        userRepo.save(user);
    }


    public String signInUserAndGenerateJWT(@Valid User user) {
        authenticateUser(user);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
        return AuthUtils.generateJWT(userDetails.getUsername(),authenticationSigningSecret);
    }

    private void authenticateUser(User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        } catch (DisabledException | BadCredentialsException ex) {
            log.debug("User login attempt failed for {} reason : {}", user.getUserName(), ex.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "RecipeAppConstants.USER_NOT_ALLOWED_OPERATION");
        }
    }


}

package com.store.book.controller;

import com.store.book.domain.UserResponse;
import com.store.book.domain.UserDTO;
import com.store.book.entities.User;
import com.store.book.repository.RoleRepository;
import com.store.book.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

/**
 * User Controller
 *
 * @author Amit Vs
 */
@RestController
@RequestMapping("/bookstore")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }


    /**
     * User creation
     *
     * @param userDTO user details to create
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signUp(@RequestBody @Valid UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.addRole(roleRepository.findByName("USER"));
        return userService.createUser(user);
    }


    /**
     * generate token
     *
     * @param userDTO user details to authenticate
     * @Retrun String  on successful authentication header 'authorization' with bearer token which can be later used in all requests for auth
     */
    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse generateToken(@RequestBody @Valid UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return userService.signInUserAndGenerateJWT(user);
    }


    /**
     * get details of current logged in user
     *
     * @param principal principle
     * @Retrun User
     */
    @GetMapping(value = "/logedinuser", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDetails currentUser(Principal principal) {
        return userService.loggedInUser(principal);
    }

}

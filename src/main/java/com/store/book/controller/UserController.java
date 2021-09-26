package com.store.book.controller;

import com.store.book.domain.UserCreateDTO;
import com.store.book.entities.User;
import com.store.book.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Amit Vs
 */
@RestController
@RequestMapping("/bookstore")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }

    /**
     * User creation
     *
     * @param userDTO user details to create
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid UserCreateDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userService.createUser(user);
    }


    /**
     * generate token
     *
     * @param userCreateDTO user details to authenticate
     * @Retrun String  on successful authentication header 'authorization' with bearer token which can be later used in all requests for auth
     */
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        User user = modelMapper.map(userCreateDTO, User.class);
        return userService.signInUserAndGenerateJWT(user);
    }

}

package com.store.book.controller;

import com.store.book.domain.UserResponse;
import com.store.book.domain.UserDTO;
import com.store.book.entities.Role;
import com.store.book.entities.User;
import com.store.book.repository.RoleRepository;
import com.store.book.service.UserService;
import com.store.book.util.DataFeeder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Amit Vs
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RoleRepository roleRepository;


    @Test
    public void testGenerateToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        UserResponse userResponse= UserResponse.builder()
                        .token("xgjhdgfjsdhjgfgsf")
                        .build();
        when(userService.signInUserAndGenerateJWT(any())).thenReturn(userResponse);
        UserResponse response = userController.generateToken(formUserCreateDTO());
        Assert.assertEquals("Generated Token is not expected ", "xgjhdgfjsdhjgfgsf", response.getToken());
        Assert.assertNull("User is not expected ",  response.getUserDetails());
    }

    @Test
    public void testSignUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        User user =DataFeeder.formUser();
        user.addRole(DataFeeder.formRole("USER"));

        Role role= Role.builder().name("USER").build();
        when(userService.createUser(any())).thenReturn(user);
        when(roleRepository.findByName("USER")).thenReturn(role);
        when(modelMapper.map(any(),any())).thenReturn(user);
        User response = userController.signUp(formUserCreateDTO());
        Assert.assertEquals("User is not expected ", "Admin", response.getUsername());
    }

    private UserDTO formUserCreateDTO() {
        return UserDTO.builder()
                        .username("testuser")
                        .password("password").build();

    }

}

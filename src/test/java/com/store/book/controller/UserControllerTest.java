package com.store.book.controller;

import com.store.book.domain.UserResponse;
import com.store.book.domain.UserDTO;
import com.store.book.service.UserService;
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


    @Test
    public void testGenerateToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        UserResponse userResponse= UserResponse.builder()
                        .token("xgjhdgfjsdhjgfgsf").build();
        when(userService.signInUserAndGenerateJWT(Mockito.any())).thenReturn(userResponse);
        UserResponse response = userController.generateToken(formUserCreateDTO());
        Assert.assertEquals("Generated Token is not expected ", "xgjhdgfjsdhjgfgsf", response.getToken());
    }

    private UserDTO formUserCreateDTO() {
        return UserDTO.builder()
                        .username("testuser")
                        .password("password").build();

    }

}

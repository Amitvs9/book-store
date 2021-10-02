
package com.store.book.service;

import com.store.book.entities.User;
import com.store.book.error.BookStoreException;
import com.store.book.repository.UserRepository;
import com.store.book.util.DataFeeder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


/**
 * @author Amit Vs
 */

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void init() {
        ReflectionTestUtils.setField(userService, "authenticationSigningSecret", "test-bookStore");
    }


    @Test
    public void test_UserValid_IsNewUser() {
        User user = DataFeeder.formUser();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(Mockito.any())).thenReturn(user);
        userService.createUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(user.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }


    @Test(expected = BookStoreException.class)
    public void test_UserIsValid_AlreadyExist() {
        User user = DataFeeder.formUser();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        userService.createUser(user);
    }

    @Test
    public void testSignInUserAndGenerateJWT() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        Assert.assertNotNull(userService.signInUserAndGenerateJWT(DataFeeder.formUser()));
    }

    @Test(expected = BookStoreException.class)
    public void testAuthenticateUser() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(authenticationManager.authenticate(Mockito.any())).thenThrow(new BadCredentialsException("bad credentials"));
        userService.signInUserAndGenerateJWT(DataFeeder.formUser());
    }
}

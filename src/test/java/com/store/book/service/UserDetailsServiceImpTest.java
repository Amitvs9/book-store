package com.store.book.service;

import com.store.book.entities.User;
import com.store.book.repository.UserRepository;
import com.store.book.util.DataFeeder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Amit Vs
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImpTest {

    @InjectMocks
    private UserDetailsServiceImp userDetailsServiceImp;

    @Mock
    private UserDetailsService userDetailsService;


    @Mock
    private UserRepository userRepository;


    @Test
    public void testLoadUserByUsername() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        User user =DataFeeder.formUser();
        user.addRole(DataFeeder.formRole("USER"));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Assert.assertNotNull(userDetailsServiceImp.loadUserByUsername(DataFeeder.formUser().getUsername()));
    }

    @Test
    public void testLoadUserByUsername1() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Assert.assertNull(userDetailsServiceImp.loadUserByUsername(userDetails.getUsername()));
    }

}

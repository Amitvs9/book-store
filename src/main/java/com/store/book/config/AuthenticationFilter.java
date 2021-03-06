package com.store.book.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.book.domain.UserDTO;
import com.store.book.util.AuthUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationFilter
 * Extend UsernamePasswordAuthenticationFilter and
 * overrides attemptAuthentication to use custom UserCreateDTO in request
 * overrides successfulAuthentication to generate custom header in response 'Authorization' containing JWT for further requests
 *
 * @author Amit Vs
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final String authenticationSigningSecret;
    private final AuthenticationManager authenticationManager;

    /**
     * public constructor
     * @param authenticationManager Spring authentication manager
     * @param authenticationSigningSecret Secret used for JWT signing
     */
    public AuthenticationFilter(AuthenticationManager authenticationManager, String authenticationSigningSecret) {
        this.authenticationManager = authenticationManager;
        this.authenticationSigningSecret = authenticationSigningSecret;
        setFilterProcessesUrl("/login");
    }

    /**
     * Adds custom header on successful authentication
     * @param request http request
     * @param response http response
     * @return userDTO userdto
     * @throws AuthenticationException ex
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserDTO user = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (IOException ioe) {
            throw new RuntimeException("Could not read request" + ioe);

        }
    }


    /**
     * Adds custom header on successful authentication
     * @param request http request
     * @param response http response
     * @param chain filter chains
     * @param authResult auth result
     * @throws IOException may throw while trying to serialize/deserialize http request response
     * @throws ServletException  may throw while trying to process http request response
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = AuthUtils.generateJWT(((User) authResult.getPrincipal()).getUsername(), authenticationSigningSecret);
        response.setHeader("Access-Control-Expose-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.addHeader("Authorization", "Bearer " + token);

    }
}

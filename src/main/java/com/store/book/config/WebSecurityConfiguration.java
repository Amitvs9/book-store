package com.store.book.config;

import com.store.book.service.UserDetailsServiceImp;
import com.store.book.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * WebSecurityConfiguration
 * Extends WebSecurityConfigurerAdapter and sets CORS policy custom UserDetailService
 *
 * @author Amit V
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String authenticationSigningSecret;

    private static final String[] AUTH_WHITELIST = {
                    "/h2-console/**",
                    "/",
                    "/bookstore/authenticate",
                    "/bookstore/logedinuser"

    };

    /**
     * @param http Spring HttpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                        .antMatchers(AUTH_WHITELIST).permitAll()
                        .antMatchers(HttpMethod.POST, "/bookstore/signup").permitAll()
                        .anyRequest().authenticated()
                        .and().addFilter(new AuthenticationFilter(authenticationManager(), authenticationSigningSecret))
                        .addFilter(new AuthorizationFilter(authenticationManager(), authenticationSigningSecret))
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    /**
     * Sets custom user details service in AuthenticationManagerBuilder
     *
     * @param auth Spring security AuthenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImp();
    };

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS config
     *
     * @return corsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * Creates authenticationManager Bean
     *
     * @return AuthenticationManager
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager();
    }

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper;
        modelMapper = new ModelMapper();
        return modelMapper;
    }
}


package com.ghita.booknestbackend.security;
/*
The @Configuration and @EnableWebSecurity annotations switch off the default web security configuration, and we can define our own configuration in this class. Inside the
filterChain(HttpSecurity http) method that we will see in action later, we can define which
endpoints in our application are secure and which are not. We don’t actually need this method
yet because we can use the default settings where all the endpoints are secured

We can also add in-memory users to our application by using Spring Security’s
InMemoryUserDetailsManager, which implements UserDetailsService. Then we can implement user/password authentication that is
stored in memory. We can also use PasswordEncoder to encode passwords using the bcrypt algorithm.
 */

import com.ghita.booknestbackend.config.AuthEntryPoint;
import com.ghita.booknestbackend.config.AuthenticationFilter;
import com.ghita.booknestbackend.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationFilter authenticationFilter;
    private final AuthEntryPoint exceptionHandler;

    private static final String[] SWAGGER_PATHS = {"/api-docs/**", "/swagger-ui/**"};
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthenticationFilter authenticationFilter, AuthEntryPoint exceptionHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
        this.exceptionHandler = exceptionHandler;
    }
    public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /*
    we have to configure Spring Security functionality. Spring Security’s SecurityFilterChain bean defines which paths are secured and which are not.
    We add the filterChain method to the SecurityConfig class.
    In the method, we define that the POST method request to the /login endpoint is allowed without authentication
    and that requests to all other endpoints require authentication.
    We will also define that Spring Security will never create a session, and therefore we can disable cross-site request
    forgery (csrf).
    JWTs are designed to be stateless, which reduces the risk of session-related vulnerabilities.
    We will use Lambdas in the HTTP security configuration
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .cors(withDefaults())
                .sessionManagement((sessionManagement) -> sessionManagement.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests.requestMatchers(HttpMethod.POST,
                                "/login").permitAll().anyRequest().authenticated())
                .addFilterBefore(authenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exceptionHandling) -> exceptionHandling.
                        authenticationEntryPoint(exceptionHandler));
        return http.build();
    }

    // Add Global CORS filter inside the class
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(false);
        config.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

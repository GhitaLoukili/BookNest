package com.ghita.booknestbackend.controller;

/*
We get a JSON object from the request body that contains the username and password.
AuthenticationManager is used to perform authentication and it uses credentials that we get from the request.
Then, we use the JwtService class’ getToken method to generate a JWT.
Finally, we build an HTTP response that contains the generated JWT in the Authorization header
 */


import com.ghita.booknestbackend.domain.AccountCredentials;
import com.ghita.booknestbackend.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
        Authentication auth = authenticationManager.authenticate(creds);
        // Generate token
        String jwts = jwtService.getToken(auth.getName());
        // Build response with the generated token
        return ResponseEntity.ok()
                             .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
                             .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                             .build();
    }
}

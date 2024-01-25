package com.ghita.booknestbackend.service;

/*
We will create a secret key using the jjwt library’s secretKeyFor method. This is only for
demonstration purposes. In a production environment, you should read your secret key
from the application configuration.
The getToken method then generates and returns the token.
The getAuthUser method gets the token from the response Authorization header.
Then, we will use the parserBuilder method provided by the jjwt library to create a JwtParserBuilder instance.
The setSigningKey method is used to specify a secret key for token verification.
The parseClaimsJws method removes the Bearer prefix from the Authorization header.
Finally, we will use the getSubject method to get the username.
 */

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    static final long EXPIRATIONTIME=86400000;      // 1 day in ms. Should be shorter in production.
    static final String PREFIX = "Bearer";

    // Generate secret key. Only for demonstration purposes.In production, you should read it from the application configuration.
    static final Key key = Keys.secretKeyFor (SignatureAlgorithm.HS256);
    // Generate signed JWT token
    public String getToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(key)
                .compact();
        return token;
    }
    // Get a token from request Authorization header,verify the token, and get username
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            String user = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null)
                return user;
        }
        return null;
    }
}

package com.meet.blog_post.auth.security;

import com.meet.blog_post.exception.ApplicationException;
import com.meet.blog_post.response.SuccessResponse;
import com.meet.blog_post.response.TokenResponse;
import com.meet.blog_post.user.models.User;
import com.meet.blog_post.user.service.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtServices {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    public final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B625064536756685970";

    public Optional<User> getUserDetailsFromToken(String token) {
        String username = extractUsernameFormClaims(token);
        return myUserDetailsService.findByUsername(username);
    }

    private String extractUsernameFormClaims(String token) {
        return extractClaims(token).getSubject();
    }

    private Date extractExpirationFormClaims(String token) {
        return extractClaims(token).getExpiration();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSigningKey() {
        byte[] ketBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(ketBytes);
    }

    public boolean isValidToken(String token) throws ExpiredJwtException {
        Date expDate = extractExpirationFormClaims(token);
        if (expDate.before(new Date()))
            throw new ApplicationException( "Token has expired",HttpStatus.UNAUTHORIZED);
        else
            return true;
    }

    public ResponseEntity<SuccessResponse> issueToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (86400000)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

        SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK, "success", new TokenResponse(token));
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}

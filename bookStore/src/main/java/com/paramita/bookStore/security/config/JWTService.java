package com.paramita.bookStore.security.config;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
@Slf4j
public class JWTService {
	@Value("${application.security.jwt.secrectKey}")
    private String secretKey;
	@Value("${application.security.jwt.expirationTime}")
    private Long expirationTime;
    public String generateToken(UserDetails userDetails) {
    	//userDetails.getAuthorities().stream().
    	return Jwts.builder().subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expirationTime))
    			.signWith(key())
    			.compact();
    }

    public String extractUsernameFromToken(String token){
        return this.extractUsernameFromClaims(token).getSubject();
    }
    private Claims extractUsernameFromClaims(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public Key key() {
    	byte[] secret=Decoders.BASE64.decode((CharSequence) secretKey);
    	return Keys.hmacShaKeyFor(secret);
    }

    public boolean isValid(String token,String username){
        Date expireTime = this.extractUsernameFromClaims(token).getExpiration();
        String usernameFromToken = this.extractUsernameFromToken(token);
        if(expireTime.before(new Date())){
            log.info("Token has been expired");
            return false;
        }
        return usernameFromToken!=null && usernameFromToken.equals(username);
    }
}

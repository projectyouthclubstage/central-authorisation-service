package de.youthclubstage.backend.central.authorisation.security;

import de.youthclubstage.backend.central.authorisation.exception.TokenValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyStore;
import java.util.Date;

@Component
public class TokenValidationService {

    private final String password;
    private final String alias;

    private String token;

    public TokenValidationService(@Value("${jwt.pass}") String password,
                                  @Value("${jwt.alias}") String alias) {
        this.password = password;
        this.alias = alias;
    }

    boolean validateToken(String token) {
        if(!getClaims(token).isEmpty()) {
            this.token = token;
            return true;
        } else {
            return false;
        }
    }

    public Long getUserId() {
        Claims claims = getClaims(token);
        return (Long) claims.get(TokenFields.USER_ID);
    }

    public Boolean getSystemAdministrator() {
        Claims claims = getClaims(token);
        return (Boolean) claims.get(TokenFields.SYSTEM_ADMINISTRATOR);
    }

    private Claims getClaims(String token) {
        try {

            ClassPathResource resource = new ClassPathResource("keystore.jks");
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(resource.getInputStream(), password.toCharArray());

            Key key = keystore.getKey(alias, password.toCharArray());
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before((new Date()))) {
                return claims.getBody();
            } else {
                throw new TokenValidationException(2011);
            }

        } catch(Exception e) {
            throw new TokenValidationException(2010);
        }
    }

}

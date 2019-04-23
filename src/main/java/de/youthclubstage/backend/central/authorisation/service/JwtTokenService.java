package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.service.model.TokenInformation;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyStore;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
class JwtTokenService {

    private final String password;
    private final String alias;

    private final Integer expirationMinutes;

    public JwtTokenService(@Value("${jwt.pass}") String password,
                           @Value("${jwt.alias}") String alias,
                           @Value("${jwt.expirationMinutes}") Integer expiration) {
        this.password = password;
        this.alias = alias;
        this.expirationMinutes = expiration;
    }

    String generateToken(TokenInformation information) throws Exception {


        ClassPathResource resource = new ClassPathResource("keystore.jks");
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(resource.getInputStream(), password.toCharArray());

        Key key = keystore.getKey(alias, password.toCharArray());
        Calendar expires = Calendar.getInstance();
        expires.roll(Calendar.MINUTE, expirationMinutes);

        Map<String, Object> informationMap = information.toMap();

        return Jwts.builder()
                .setClaims(informationMap)
                .setSubject("")
                .setIssuedAt(new Date())
                .setExpiration(expires.getTime())
                .signWith(SignatureAlgorithm.RS256, key)
                .compact();

    }

}

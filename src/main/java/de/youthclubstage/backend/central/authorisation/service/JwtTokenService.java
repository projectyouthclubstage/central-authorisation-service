package de.youthclubstage.backend.central.authorisation.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenService {

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

    public String generateToken() throws Exception {


        ClassPathResource resource = new ClassPathResource("keystore.jks");
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(resource.getInputStream(), password.toCharArray());

        Key key = keystore.getKey(alias, password.toCharArray());
        Certificate cert = keystore.getCertificate(alias);
        PublicKey publicKey = cert.getPublicKey();
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", "cope");
        Calendar expires = Calendar.getInstance();
        expires.roll(Calendar.MINUTE, expirationMinutes);

        String retValue = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expires.getTime())
                .signWith(SignatureAlgorithm.RS256, key)
                .compact();

        retValue = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("a")
                .setIssuedAt(new Date())
                .setExpiration(expires.getTime())
                .signWith(SignatureAlgorithm.RS256, key)
                .setPayload("string")
                .compact();

        return retValue;
    }

}

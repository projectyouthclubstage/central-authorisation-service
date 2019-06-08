package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.entity.Member;
import de.youthclubstage.backend.central.authorisation.entity.UserGroupAssignment;
import de.youthclubstage.backend.central.authorisation.security.TokenFields;
import de.youthclubstage.backend.central.authorisation.security.TokenValidationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyStore;
import java.util.*;

@Component
class TokenCreationService {

    private final String password;
    private final String alias;
    private final Integer expirationMinutes;

    @Autowired
    private TokenValidationService tokenValidationService;

    public TokenCreationService(@Value("${jwt.pass}") String password,
                                @Value("${jwt.alias}") String alias,
                                @Value("${jwt.expirationMinutes}") Integer expiration) {
        this.password = password;
        this.alias = alias;
        this.expirationMinutes = expiration;
    }

    String generateToken(Member member, List<UserGroupAssignment> groups) throws Exception {
        return build(
                tokenValidationService.getUserId(), tokenValidationService.getSystemAdministrator(), member, groups);
    }

    String generateToken(ExternalUser user) throws Exception {
        return build(user.getUserId(), user.getSystemAdmin(), null, new ArrayList<>());
    }

    private String build(
            Long userId, Boolean systemAdministrator, Member member, List<UserGroupAssignment> groups) throws Exception {

        ClassPathResource resource = new ClassPathResource("keystore.jks");
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(resource.getInputStream(), password.toCharArray());

        Key key = keystore.getKey(alias, password.toCharArray());
        Calendar expires = Calendar.getInstance();
        expires.roll(Calendar.MINUTE, expirationMinutes);

        Map<String, Object> informationMap = new HashMap<>();

        informationMap.put(TokenFields.USER_ID, userId);
        informationMap.put(TokenFields.SYSTEM_ADMINISTRATOR, systemAdministrator);

        if(member != null) {
            informationMap.put(TokenFields.ORGANISATION_ID, member.getOrganisationId());
            informationMap.put(TokenFields.ORGANISATION_ADMINISTRATOR, member.getAdministrator());
            informationMap.put(TokenFields.ORGANISATION_MODERATOR, member.getModerator());
        }

        List<Long> groupIds = new ArrayList<>();
        for(UserGroupAssignment group : groups) {
            groupIds.add(group.getGroupId());
        }
        informationMap.put(TokenFields.GROUPS, groupIds);

        return Jwts.builder()
                .setClaims(informationMap)
                .setSubject("")
                .setIssuedAt(new Date())
                .setExpiration(expires.getTime())
                .signWith(SignatureAlgorithm.RS256, key)
                .compact();
    }

}

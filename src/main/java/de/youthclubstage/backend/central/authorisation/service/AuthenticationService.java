package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.service.model.TokenInformation;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private JwtTokenService jwtTokenService;

    public AuthenticationService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    public Optional<TokenDto> generateJWTToken() {

        try {
            return Optional.of(new TokenDto(jwtTokenService.generateToken(new TokenInformation())));
        } catch (Exception e) {
            System.err.println(e);
            return Optional.empty();
        }

    }
}

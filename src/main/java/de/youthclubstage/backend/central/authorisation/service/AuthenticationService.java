package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
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
            return Optional.of(new TokenDto(jwtTokenService.generateToken()));
        } catch (Exception e) {
            System.err.println(e);
            return Optional.empty();
        }

    }
}

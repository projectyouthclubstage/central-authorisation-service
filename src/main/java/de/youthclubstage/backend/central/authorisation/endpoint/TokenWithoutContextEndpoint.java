package de.youthclubstage.backend.central.authorisation.endpoint;

import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.service.ExternalAuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/token")
@Api(tags = "Token - without context")
public class TokenWithoutContextEndpoint {

    private final ExternalAuthenticationService externalAuthenticationService;

    @Autowired
    public TokenWithoutContextEndpoint(ExternalAuthenticationService externalAuthenticationService) {

        this.externalAuthenticationService = externalAuthenticationService;
    }

    @ApiResponses({
        @ApiResponse(
                code = 200,
                message = "Authorisation successful",
                response = TokenDto.class),
        @ApiResponse(
                code = 403,
                message = "External or internal authentication failed")})
    @PostMapping(value = "/facebook")
    public ResponseEntity<TokenDto> getTokenByFacebookLogin(@RequestBody String idToken) {

        Optional<TokenDto> token = externalAuthenticationService.getFacebookProfileInfo(idToken);

        if(token.isPresent()) {
            return ResponseEntity.ok(token.get());
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ApiResponses({
        @ApiResponse(
                code = 200,
                message = "Authorisation successful",
                response = TokenDto.class),
        @ApiResponse(
                code = 403,
                message = "External or internal authentication failed.")})
    @PostMapping(value = "/google")
    public ResponseEntity<TokenDto> getTokenByGoogleLogin(@RequestBody String idToken) {

        Optional<TokenDto> token = externalAuthenticationService.getTokenByGoogleLogin(idToken);

        if(token.isPresent()) {
            return ResponseEntity.ok(token.get());
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}

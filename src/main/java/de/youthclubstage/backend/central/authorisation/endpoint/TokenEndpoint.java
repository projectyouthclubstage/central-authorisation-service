package de.youthclubstage.backend.central.authorisation.endpoint;

import de.youthclubstage.backend.central.authorisation.endpoint.model.ErrorDto;
import de.youthclubstage.backend.central.authorisation.endpoint.model.ProviderUserDto;
import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.service.ExternalUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/token")
@Api(tags = "Token")
public class TokenEndpoint {

    private final ExternalUserService externalUserService;

    public TokenEndpoint(ExternalUserService externalUserService) {
        this.externalUserService = externalUserService;
    }

    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Authorisation successful",
                    response = TokenDto.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "There was an error",
                    response = ErrorDto.class
            )
    })
    @PostMapping(value = "/organisation/{organisation-id}")
    public ResponseEntity<TokenDto> getTokenWithContext(
            @PathVariable(name = "organisation-id") Long organisationId,
            @RequestBody ProviderUserDto providerUser) {

        Optional<TokenDto> token = externalUserService.getTokenForExternalUserWithContext(
                providerUser.getProviderId(), providerUser.getProvider(), organisationId);

        if (token.isPresent()) {
            return ResponseEntity.ok(token.get());
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Authorisation successful",
                    response = TokenDto.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "There was an error",
                    response = ErrorDto.class
            )
    })
    @PostMapping(value = "/")
    public ResponseEntity<TokenDto> getTokenWithoutContext(@RequestBody ProviderUserDto providerUser) {
        Optional<TokenDto> token = externalUserService.getTokenForExternalUserWithoutContext(
                providerUser.getProviderId(), providerUser.getProvider());


        if (token.isPresent()) {
            return ResponseEntity.ok(token.get());
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
}

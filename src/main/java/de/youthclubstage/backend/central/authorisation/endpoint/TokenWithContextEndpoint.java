package de.youthclubstage.backend.central.authorisation.endpoint;

import de.youthclubstage.backend.central.authorisation.endpoint.model.ErrorDto;
import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.service.ContextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/token/organisation")
@Api(tags = "Token")
public class TokenWithContextEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenWithContextEndpoint.class);

    private final ContextService contextService;

    @Autowired
    public TokenWithContextEndpoint(ContextService contextService) {

        this.contextService = contextService;
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
    @GetMapping(value = "/{organisation-id}")
    public ResponseEntity<TokenDto> getTokenForOrganisation(
            @PathVariable(name = "organisation-id") Long organisationId) {

        Optional<TokenDto> token = contextService.getTokenForOrganisationId(organisationId);

        if (token.isPresent()) {
            LOGGER.debug("Login for context succeeded.");
            return ResponseEntity.ok(token.get());
        } else {
            LOGGER.debug("Login for context failed.");
            return ResponseEntity.badRequest().build();
        }

    }

}

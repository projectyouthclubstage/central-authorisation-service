package de.youthclubstage.backend.central.authorisation.endpoint;

import de.youthclubstage.backend.central.authorisation.endpoint.model.ErrorDto;
import de.youthclubstage.backend.central.authorisation.endpoint.model.OrganisationDto;
import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.security.SecurityInterceptor;
import de.youthclubstage.backend.central.authorisation.service.ContextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(SecurityInterceptor.AUTH_PATH)
@Api(tags = "Token - with context")
public class TokenWithContextEndpoint {

    private final ContextService contextService;

    @Autowired
    public TokenWithContextEndpoint(ContextService contextService) {

        this.contextService = contextService;
    }

    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "List of organisations",
                    response = OrganisationDto.class,
                    responseContainer = "List"
            ), @ApiResponse(
                    code = 201,
                    message = "No organisations for this user",
                    response = ErrorDto.class
            ), @ApiResponse(
                    code = 400,
                    message = "There was an error",
                    response = ErrorDto.class
            )
    })
    @GetMapping(value = "/token/organisations")
    public ResponseEntity<List<OrganisationDto>> getOrganisationsForToken() {

        List<OrganisationDto> organisations = contextService.getOrganisations();
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

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
    @GetMapping(value = "/token/organisations/{organisation-id}")
    public ResponseEntity<TokenDto> getTokenForOrganisationId(
            @PathVariable(name = "organisation-id") Long organisationId) {

        Optional<TokenDto> token = contextService.getTokenForOrganisationId(organisationId);

        if(token.isPresent()) {
            return ResponseEntity.ok(token.get());
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

}

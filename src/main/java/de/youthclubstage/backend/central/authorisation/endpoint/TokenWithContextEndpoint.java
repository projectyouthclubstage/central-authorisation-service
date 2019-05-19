package de.youthclubstage.backend.central.authorisation.endpoint;

import de.youthclubstage.backend.central.authorisation.endpoint.model.ErrorDto;
import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.service.ContextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/token")
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
                    message = "Authorisation successful",
                    response = TokenDto.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "There was an error",
                    response = ErrorDto.class
            )
    })
    @GetMapping(value = "/organisations/{organisation-id}")
    public ResponseEntity<TokenDto> getTokenForOrganisationId(
            @PathVariable(name = "organisation-id") Long organisationId) {

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}

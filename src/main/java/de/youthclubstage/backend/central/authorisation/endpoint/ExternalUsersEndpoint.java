package de.youthclubstage.backend.central.authorisation.endpoint;

import de.youthclubstage.backend.central.authorisation.endpoint.model.ErrorDto;
import de.youthclubstage.backend.central.authorisation.endpoint.model.ProviderUserDto;
import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenInformation;
import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.entity.Provider;
import de.youthclubstage.backend.central.authorisation.service.ExternalUserService;
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
@RequestMapping("/external-users")
@Api(tags = "External Users")
public class ExternalUsersEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalUsersEndpoint.class);

    private final ExternalUserService externalUserService;

    @Autowired
    public ExternalUsersEndpoint(ExternalUserService externalUserService) {
        this.externalUserService = externalUserService;
    }

    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "A list of external users",
                    response = ExternalUser.class,
                    responseContainer = "List"
            )
    })
    @GetMapping(value = "/allUsers")
    public ResponseEntity<Iterable<ExternalUser>> getAllExternalUsers() {

        return ResponseEntity.ok(externalUserService.getExternalUsers());
    }



    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "User is here"
            ),
            @ApiResponse(
                    code = 404,
                    message = "There was an error",
                    response = ErrorDto.class
            )
    })
    @PostMapping(value = "/organisation/{organisation-id}")
    public ResponseEntity createExternalUser(
            @PathVariable(name = "organisation-id") Long organisationId,
            @RequestBody ProviderUserDto providerUser) {

        Optional<TokenInformation> token = externalUserService.getTokenInformationForExternalUser(
                providerUser.getProviderId(), providerUser.getProvider(), organisationId);

        if (token.isPresent()) {
            return ResponseEntity.ok(token.get());
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}

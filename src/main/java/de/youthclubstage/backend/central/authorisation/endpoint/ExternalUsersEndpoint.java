package de.youthclubstage.backend.central.authorisation.endpoint;

import de.youthclubstage.backend.central.authorisation.endpoint.model.ErrorDto;
import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.service.ExternalUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping(value = "getAll")
    public ResponseEntity<Iterable<ExternalUser>> getAllExternalUsers() {
        LOGGER.trace("getAllExternalUsers");

        return ResponseEntity.ok(externalUserService.getExternalUsers());
    }



    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "A new user is created"
            ),
            @ApiResponse(
                    code = 400,
                    message = "There was an error",
                    response = ErrorDto.class
            )
    })
    @GetMapping(value = "createNew")
    public ResponseEntity createExternalUser() {
        LOGGER.trace("getAllExternalUsers");

        if (externalUserService.createUser()) {
            LOGGER.debug("is created");
            return ResponseEntity.ok().build();
        } else {
            LOGGER.info("is not created");
            return ResponseEntity.badRequest().build();
        }

    }

}

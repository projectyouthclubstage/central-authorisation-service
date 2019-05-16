package de.youthclubstage.backend.central.authorisation.endpoint;

import de.youthclubstage.backend.central.authorisation.endpoint.model.ProviderUserDto;
import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.service.ExternalUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/external-users")
@Api(tags = "External Users")
public class ExternalUsersEndpoint {

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
    @GetMapping(value = "/")
    public ResponseEntity<Iterable<ExternalUser>> getAllExternalUsers() {

        return ResponseEntity.ok(externalUserService.getExternalUsers());
    }

    @ApiResponses({
            @ApiResponse(
                    code = 201,
                    message = "A list of external users",
                    response = ExternalUser.class,
                    responseContainer = "List"
            )
    })
    @PostMapping(value = "/")
    public ResponseEntity<ExternalUser> getAllExternalUsers(@RequestBody ProviderUserDto toCreate) {

        ExternalUser created = externalUserService.createExternalUser(toCreate);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }



}

package de.youthclubstage.backend.central.authorisation.endpoint;

import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.service.ExternalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/external-users")
public class ExternalUsersEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalUsersEndpoint.class);

    private final ExternalUserService externalUserService;

    @Autowired
    public ExternalUsersEndpoint(ExternalUserService externalUserService) {
        this.externalUserService = externalUserService;
    }

    @RequestMapping(value = "getAll")
    public ResponseEntity<Iterable<ExternalUser>> getAllExternalUsers() {
        LOGGER.trace("getAllExternalUsers");

        return ResponseEntity.ok(externalUserService.getExternalUsers());
    }

    @RequestMapping(value = "createNew")
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

package de.youthclubstage.backend.central.authorisation.endpoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


//TODO Delete whole class - just here while there is no token

@ApiModel(value = "TokenInformation")
@Data
public class TokenInformation {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("system_admin")
    private Boolean systemAdministrator = false;

    @JsonProperty("organisation_id")
    private Long organisationId;

    @JsonProperty("organisation_administrator")
    private Boolean organisationAdministrator = false;

    @JsonProperty("organisation_moderator")
    private Boolean organisationModerator = false;

    @JsonProperty("groups")
    private List<Long> groups = new ArrayList<>();

}
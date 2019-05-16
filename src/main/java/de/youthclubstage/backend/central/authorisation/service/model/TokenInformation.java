package de.youthclubstage.backend.central.authorisation.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


//TODO Delete whole class - just here while there is no token

@ApiModel(value = "TokenInformation")
@Data
@AllArgsConstructor
@NoArgsConstructor
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



    public Map<String, Object> toMap() {
        Map<String, Object> retValue = new HashMap<>();

        retValue.put("user_id", this.getUserId());
        retValue.put("system_administrator", this.getSystemAdministrator());
        retValue.put("organisation_id", this.getOrganisationId());
        retValue.put("organisation_administrator", this.getOrganisationAdministrator());
        retValue.put("organisation_moderator", this.getOrganisationModerator());
        retValue.put("groups", this.getGroups());

        return retValue;
    }

}
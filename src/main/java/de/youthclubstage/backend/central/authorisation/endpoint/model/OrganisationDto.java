package de.youthclubstage.backend.central.authorisation.endpoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "Organisation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("administrator")
    private Boolean administrator;

    @JsonProperty("moderator")
    private Boolean moderator;

}
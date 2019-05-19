package de.youthclubstage.backend.central.authorisation.endpoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.youthclubstage.backend.central.authorisation.entity.Provider;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


//TODO Delete whole class - just here while there is no token

@ApiModel(value = "ProviderUser")
@Data
public class ProviderUserDto {

    @JsonProperty("provider")
    private Provider provider;

    @JsonProperty("provider_id")
    private String providerId;

}
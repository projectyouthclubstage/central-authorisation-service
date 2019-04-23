package de.youthclubstage.backend.central.authorisation.endpoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel(value = "Token")
@Data
@AllArgsConstructor
public class TokenDto {

    @JsonProperty("token")
    private String token;

}

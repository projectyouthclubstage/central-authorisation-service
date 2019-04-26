package de.youthclubstage.backend.central.authorisation.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "Facebook-Data")
@Data
public class FacebookData {

    @JsonProperty("id")
    private String id;

}
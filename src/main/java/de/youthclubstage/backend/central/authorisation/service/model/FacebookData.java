package de.youthclubstage.backend.central.authorisation.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "Facebook-Data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacebookData {

    @JsonProperty("id")
    private String id;

}
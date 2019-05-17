package de.youthclubstage.backend.central.authorisation.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(value = "Google-Data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleData {

    @JsonProperty("id")
    private String id;

}
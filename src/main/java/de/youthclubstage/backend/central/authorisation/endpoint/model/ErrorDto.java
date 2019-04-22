package de.youthclubstage.backend.central.authorisation.endpoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "Error")
@Data
public class ErrorDto {

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    @JsonProperty("request_id")
    private String requestId;

}

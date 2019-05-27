package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.service.model.GoogleData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Google-Service", url = "https://www.googleapis.com/oauth2/v3")
public interface GoogleService {

    @GetMapping(value = "/tokeninfo")
    GoogleData getTokenInfoForToken(@RequestParam("id_token") String idToken);


}

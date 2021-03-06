package de.youthclubstage.backend.central.authorisation.service;

import feign.HeaderMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(name = "Google-Service", url = "https://www.googleapis.com/oauth2/v3")
public interface GoogleService {

    @PostMapping(value = "/tokeninfo")
    Object getTokenInfoForToken(@RequestParam("idToken") String idToken, @HeaderMap Map header);

}

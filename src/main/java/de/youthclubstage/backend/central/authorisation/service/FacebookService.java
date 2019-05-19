package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.service.model.FacebookData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "Google-Service", url = "https://graph.facebook.com")
public interface FacebookService {

    @GetMapping(value = "/me")
    FacebookData getTokenInfoForToken(@RequestParam("access_token") String idToken,
                                      @RequestParam("fields") String fields);


}

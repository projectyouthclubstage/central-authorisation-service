package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.entity.Provider;
import de.youthclubstage.backend.central.authorisation.exception.ExternalAuthenticationFailedException;
import de.youthclubstage.backend.central.authorisation.exception.TokenCreationException;
import de.youthclubstage.backend.central.authorisation.repository.ExternalUserRepository;
import de.youthclubstage.backend.central.authorisation.service.model.FacebookData;
import de.youthclubstage.backend.central.authorisation.service.model.GoogleData;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ExternalAuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalAuthenticationService.class);

    private final GoogleService googleService;
    private final FacebookService facebookService;
    private final TokenCreationService tokenCreationService;

    private final ExternalUserRepository externalUserRepository;

    @Autowired
    public ExternalAuthenticationService(GoogleService googleService,
                                         FacebookService facebookService,
                                         TokenCreationService tokenCreationService,
                                         ExternalUserRepository externalUserRepository) {

        this.googleService = googleService;
        this.facebookService = facebookService;
        this.tokenCreationService = tokenCreationService;

        this.externalUserRepository = externalUserRepository;
    }


    public Optional<TokenDto> getTokenByGoogleLogin(String idToken) {

        GoogleData googleResponse;

        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Length", "0");

            Object a = googleService.getTokenInfoForToken(idToken, headers);
            googleResponse = (GoogleData) a;
        } catch (FeignException e) {
            String msg = String.format(
                    "Exception while calling Google-Service: %s",
                    e);
            LOG.info(msg);
            throw new ExternalAuthenticationFailedException("CAS#0011", "Google-authentication failed.");
        }


        if (googleResponse.getId() == null || googleResponse.getId().isEmpty()) {
            LOG.info("Google-Id returned empty.");
            throw new ExternalAuthenticationFailedException("CAS#0012", "Google-authentication failed.");
        } else {
            LOG.info("Successfully external login.");
        }

        return this.getInternalAuthenticationForService(googleResponse.getId(), Provider.GOOGLE);
    }


    public Optional<TokenDto> getTokenByFacebookLogin(final String accessToken) {

        FacebookData facebookResponse;

        try {
             facebookResponse = facebookService.getTokenInfoForToken(accessToken, "id");
        } catch (FeignException e) {
            String msg = String.format(
                    "Exception while calling Facebook-Service: %s",
                    e);
            LOG.info(msg);
            throw new ExternalAuthenticationFailedException("CAS#0021", "Facebook-authentication failed.");
        }


        if (facebookResponse.getId() == null || facebookResponse.getId().isEmpty()) {
            LOG.info("Facebook-Id returned empty.");
            throw new ExternalAuthenticationFailedException("CAS#0022", "Facebook-authentication failed.");
        } else {
            LOG.info("Successfully external login.");
        }

        return this.getInternalAuthenticationForService(facebookResponse.getId(), Provider.FACEBOOK);
    }

    private Optional<TokenDto> getInternalAuthenticationForService(String providerId, Provider provider) {

        Optional<ExternalUser> user = externalUserRepository.findByProviderIdAndProviderType(providerId, provider);

        if (!user.isPresent()) {
            return Optional.empty();
        }


        try {
            return Optional.of(new TokenDto(tokenCreationService.generateToken(user.get())));
        } catch (Exception e) {
            String msg = String.format(
                    "Exception while creating Token : %s",
                    e);
            LOG.error(msg);
            throw new TokenCreationException("CAS#003", "Could not create JWT");
        }

    }



}

package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.entity.Provider;
import de.youthclubstage.backend.central.authorisation.exception.ExternalAuthenticationFailedException;
import de.youthclubstage.backend.central.authorisation.exception.TokenCreationException;
import de.youthclubstage.backend.central.authorisation.repository.ExternalUserRepository;
import de.youthclubstage.backend.central.authorisation.service.mapper.TokenInformationMapper;
import de.youthclubstage.backend.central.authorisation.service.model.FacebookData;
import de.youthclubstage.backend.central.authorisation.service.model.GoogleData;
import de.youthclubstage.backend.central.authorisation.service.model.TokenInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalAuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalAuthenticationService.class);

    private final GoogleService googleService;
    private final FacebookService facebookService;
    private final JwtTokenService jwtTokenService;

    private final ExternalUserRepository externalUserRepository;

    @Autowired
    public ExternalAuthenticationService(GoogleService googleService,
                                         FacebookService facebookService,
                                         JwtTokenService jwtTokenService,
                                         ExternalUserRepository externalUserRepository) {

        this.googleService = googleService;
        this.facebookService = facebookService;
        this.jwtTokenService = jwtTokenService;

        this.externalUserRepository = externalUserRepository;
    }


    public Optional<TokenDto> getTokenByGoogleLogin(String idToken) {

        GoogleData googleResponse = googleService.getTokenInfoForToken(idToken);

        if (googleResponse.getId().isEmpty()) {
            throw new ExternalAuthenticationFailedException("CAS#001", "Google-id returned empty.");
        } else {
            LOGGER.info("Successfully external login");
        }

        return this.getInternalAuthenticationForService(googleResponse.getId(), Provider.GOOGLE);

    }


    public Optional<TokenDto> getFacebookProfileInfo(final String accessToken) {

        FacebookData facebookResponse = facebookService.getTokenInfoForToken(accessToken, "id");

        if (facebookResponse.getId().isEmpty()) {
            throw new ExternalAuthenticationFailedException("CAS#002", "Facebook-id returned empty.");
        } else {
            LOGGER.info("Successfully external login");
        }

        return this.getInternalAuthenticationForService(facebookResponse.getId(), Provider.FACEBOOK);

    }

    private Optional<TokenDto> getInternalAuthenticationForService(String providerId, Provider provider) {

        Optional<ExternalUser> user = externalUserRepository.findByProviderIdAndProviderType(providerId, provider);

        if (!user.isPresent()) {
            return Optional.empty();
        }

        Optional<TokenInformation> information =  TokenInformationMapper.toTokenInformation(user.get());

        if (!information.isPresent()) {
            return Optional.empty();
        } else {
            try {
                return Optional.of(new TokenDto(jwtTokenService.generateToken(information.get())));
            } catch (Exception e) {
                String msg = String.format(
                        "Exception while creating Token : %s",
                        e);
                LOGGER.error(msg);
                throw new TokenCreationException("CAS#003", "Could not create JWT");
            }
        }

    }



}

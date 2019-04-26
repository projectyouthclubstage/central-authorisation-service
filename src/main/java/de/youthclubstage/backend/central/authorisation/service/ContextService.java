package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.entity.Member;
import de.youthclubstage.backend.central.authorisation.entity.Provider;
import de.youthclubstage.backend.central.authorisation.entity.UserGroupAssignment;
import de.youthclubstage.backend.central.authorisation.exception.TokenCreationException;
import de.youthclubstage.backend.central.authorisation.repository.ExternalUserRepository;
import de.youthclubstage.backend.central.authorisation.repository.MemberRepository;
import de.youthclubstage.backend.central.authorisation.repository.UserGroupAssignmentRepository;
import de.youthclubstage.backend.central.authorisation.service.mapper.TokenInformationMapper;
import de.youthclubstage.backend.central.authorisation.service.model.TokenInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContextService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextService.class);

    private static final Long NON_EXISTING_ORGANISATION = -1L;

    private final MemberRepository memberRepository;
    private final UserGroupAssignmentRepository userGroupAssignmentRepository;

    private final JwtTokenService jwtTokenService;

    @Autowired
    public ContextService(MemberRepository memberRepository,
                          UserGroupAssignmentRepository userGroupAssignmentRepository,
                          JwtTokenService jwtTokenService) {

        this.memberRepository = memberRepository;
        this.userGroupAssignmentRepository = userGroupAssignmentRepository;

        this.jwtTokenService = jwtTokenService;

    }


    public Optional<TokenDto> getTokenForOrganisationId(Long organisationId) {
/*
        SpringSecurit

        Optional<TokenInformation> information =
                this.getTokenInformationForExternalUser(providerId, provider, organisationId);

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
                throw new TokenCreationException("C-AS#0001-01", "Could not create JWT");
            }
        }
    }

    public Optional<TokenDto> getTokenForExternalUserWithoutContext(String providerId, Provider provider) {

        Optional<TokenInformation> information =
                this.getTokenInformationForExternalUser(providerId, provider, NON_EXISTING_ORGANISATION);

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
                throw new TokenCreationException("C-AS#0001-02", "Could not create JWT");
            }
        }
    }

    public Optional<TokenInformation> getTokenInformationForExternalUser(
            String providerId, Provider provider, Long organisationId) {

        Optional<ExternalUser> user = externalUserRepository.findByProviderIdAndProviderType(providerId, provider);

        if (!user.isPresent()) {
            return Optional.empty();
        }

        Optional<Member> member =
                memberRepository.findByUserIdAndOrganisationId(user.get().getUserId(), organisationId);

        if (!member.isPresent()) {
            return TokenInformationMapper.toTokenInformation(user.get());
        } else {

            List<UserGroupAssignment> groups = userGroupAssignmentRepository.
                    findAllByUserIdAndOrganisationId(user.get().getUserId(), organisationId);

            return TokenInformationMapper.toTokenInformation(user.get(), member.get(), groups);
        }

 */
        return Optional.empty();
    }



}

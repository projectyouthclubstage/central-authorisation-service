package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.exception.TokenCreationException;
import de.youthclubstage.backend.central.authorisation.service.model.TokenInformation;
import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.entity.Member;
import de.youthclubstage.backend.central.authorisation.entity.Provider;
import de.youthclubstage.backend.central.authorisation.entity.UserGroupAssignment;
import de.youthclubstage.backend.central.authorisation.repository.ExternalUserRepository;
import de.youthclubstage.backend.central.authorisation.repository.MemberRepository;
import de.youthclubstage.backend.central.authorisation.repository.UserGroupAssignmentRepository;
import de.youthclubstage.backend.central.authorisation.service.mapper.TokenInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExternalUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalUserService.class);

    private static final Long NON_EXISTING_ORGANISATION = -1L;

    private final ExternalUserRepository externalUserRepository;
    private final MemberRepository memberRepository;
    private final UserGroupAssignmentRepository userGroupAssignmentRepository;

    private final JwtTokenService jwtTokenService;

    @Autowired
    public ExternalUserService(
            ExternalUserRepository externalUserRepository,
            MemberRepository memberRepository,
            UserGroupAssignmentRepository userGroupAssignmentRepository,
            JwtTokenService jwtTokenService) {

        this.externalUserRepository = externalUserRepository;
        this.memberRepository = memberRepository;
        this.userGroupAssignmentRepository = userGroupAssignmentRepository;

        this.jwtTokenService = jwtTokenService;

        init();
    }


    public Optional<TokenDto> getTokenForExternalUserWithContext(
            String providerId, Provider provider, Long organisationId) {

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
    }






    private void init() {

        // TODO Extract to initial sql

        ExternalUser user1 = new ExternalUser();
        user1.setUserId(1L);
        user1.setSystemAdmin(false);
        user1.setProviderType(Provider.FACEBOOK);
        user1.setProviderId("facebook");
        externalUserRepository.save(user1);


        ExternalUser user2 = new ExternalUser();
        user2.setUserId(2L);
        user2.setSystemAdmin(true);
        user2.setProviderType(Provider.GOOGLE);
        user2.setProviderId("google");
        externalUserRepository.save(user2);


        ExternalUser user3 = new ExternalUser();
        user3.setUserId(3L);
        user3.setSystemAdmin(false);
        user3.setProviderType(Provider.FACEBOOK);
        user3.setProviderId("test");
        externalUserRepository.save(user3);

        Member admin = new Member();
        admin.setId(1L);
        admin.setOrganisationId(1L);
        admin.setUserId(3L);
        admin.setModerator(true);
        admin.setAdministrator(true);
        memberRepository.save(admin);

        Member member = new Member();
        member.setId(2L);
        member.setOrganisationId(2L);
        member.setUserId(1L);
        member.setModerator(true);
        member.setAdministrator(false);
        memberRepository.save(member);

        UserGroupAssignment userGroupAssignment1 = new UserGroupAssignment();
        userGroupAssignment1.setGroupId(11L);
        userGroupAssignment1.setId(1L);
        userGroupAssignment1.setOrganisationId(2L);
        userGroupAssignment1.setUserId(1L);
        userGroupAssignmentRepository.save(userGroupAssignment1);

        UserGroupAssignment userGroupAssignment2 = new UserGroupAssignment();
        userGroupAssignment2.setGroupId(22L);
        userGroupAssignment2.setId(2L);
        userGroupAssignment2.setOrganisationId(2L);
        userGroupAssignment2.setUserId(1L);
        userGroupAssignmentRepository.save(userGroupAssignment2);

        UserGroupAssignment userGroupAssignment3 = new UserGroupAssignment();
        userGroupAssignment3.setGroupId(33L);
        userGroupAssignment3.setId(3L);
        userGroupAssignment3.setOrganisationId(3L);
        userGroupAssignment3.setUserId(1L);
        userGroupAssignmentRepository.save(userGroupAssignment3);

    }

    public Iterable<ExternalUser> getExternalUsers() {
        //TODO delete method - internals to external-access-point
        return externalUserRepository.findAll();
    }


}

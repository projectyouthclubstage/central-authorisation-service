package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.endpoint.model.OrganisationDto;
import de.youthclubstage.backend.central.authorisation.endpoint.model.TokenDto;
import de.youthclubstage.backend.central.authorisation.entity.Member;
import de.youthclubstage.backend.central.authorisation.entity.UserGroupAssignment;
import de.youthclubstage.backend.central.authorisation.exception.TokenCreationException;
import de.youthclubstage.backend.central.authorisation.exception.TokenValidationException;
import de.youthclubstage.backend.central.authorisation.repository.MemberRepository;
import de.youthclubstage.backend.central.authorisation.repository.UserGroupAssignmentRepository;
import de.youthclubstage.backend.central.authorisation.security.TokenValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContextService {

    private static final Logger LOG = LoggerFactory.getLogger(ContextService.class);

    private final MemberRepository memberRepository;
    private final UserGroupAssignmentRepository userGroupAssignmentRepository;

    private final TokenValidationService tokenValidationService;
    private final TokenCreationService tokenCreationService;

    @Autowired
    public ContextService(MemberRepository memberRepository,
                          UserGroupAssignmentRepository userGroupAssignmentRepository,
                          TokenValidationService tokenValidationService,
                          TokenCreationService tokenCreationService) {

        this.memberRepository = memberRepository;
        this.userGroupAssignmentRepository = userGroupAssignmentRepository;

        this.tokenValidationService = tokenValidationService;
        this.tokenCreationService = tokenCreationService;

    }

    public List<OrganisationDto> getOrganisations() {
        Long userId = this.tokenValidationService.getUserId();
        List<Member> members = this.memberRepository.findAllByUserId(userId);

        List<OrganisationDto> retValue = new ArrayList<>();
        for(Member member : members) {
            retValue.add(
                    new OrganisationDto(member.getOrganisationId(), member.getAdministrator(), member.getModerator()));
        }
        return retValue;
    }

    public Optional<TokenDto> getTokenForOrganisationId(Long organisationId) {
        Long userId = this.tokenValidationService.getUserId();
        Optional<Member> member = this.memberRepository.findByUserIdAndOrganisationId(userId, organisationId);
        if(!member.isPresent()) {
            return Optional.empty();
        }

        List<UserGroupAssignment> groups = this.userGroupAssignmentRepository.
                findAllByUserIdAndOrganisationId(userId, member.get().getOrganisationId());

        try {
            return Optional.of(new TokenDto(this.tokenCreationService.generateToken(member.get(), groups)));
        } catch (Exception e) {
            String msg = String.format(
                    "Exception while creating Token : %s",
                    e);
            LOG.error(msg);
            throw new TokenCreationException("CAS#003", "Could not create JWT");
        }

    }



}

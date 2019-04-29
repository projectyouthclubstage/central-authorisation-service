package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.entity.Member;
import de.youthclubstage.backend.central.authorisation.entity.Provider;
import de.youthclubstage.backend.central.authorisation.entity.UserGroupAssignment;
import de.youthclubstage.backend.central.authorisation.repository.ExternalUserRepository;
import de.youthclubstage.backend.central.authorisation.repository.MemberRepository;
import de.youthclubstage.backend.central.authorisation.repository.UserGroupAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalUserService {


    private final ExternalUserRepository externalUserRepository;
    private final MemberRepository memberRepository;
    private final UserGroupAssignmentRepository userGroupAssignmentRepository;

    @Autowired
    public ExternalUserService(
            ExternalUserRepository externalUserRepository,
            MemberRepository memberRepository,
            UserGroupAssignmentRepository userGroupAssignmentRepository) {

        this.externalUserRepository = externalUserRepository;
        this.memberRepository = memberRepository;
        this.userGroupAssignmentRepository = userGroupAssignmentRepository;


        init();
    }

    private void init() {

        // TODO Extract to initial sql

        ExternalUser user1 = new ExternalUser();
        user1.setUserId(1L);
        user1.setSystemAdmin(false);
        user1.setProviderType(Provider.FACEBOOK);
        user1.setProviderId("2091312604321293");
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

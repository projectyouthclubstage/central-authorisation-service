package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.endpoint.model.ProviderUserDto;
import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.repository.ExternalUserRepository;
import de.youthclubstage.backend.central.authorisation.repository.MemberRepository;
import de.youthclubstage.backend.central.authorisation.repository.UserGroupAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    }


    public Iterable<ExternalUser> getExternalUsers() {
        //TODO delete method - internals to external-access-point
        return externalUserRepository.findAll();
    }

    public ExternalUser createExternalUser(ProviderUserDto toCreate) {
        ExternalUser externalUser = new ExternalUser();
        externalUser.setProviderId(toCreate.getProviderId());
        externalUser.setProviderType(toCreate.getProvider());

        externalUserRepository.save(externalUser);

        Optional<ExternalUser> created = externalUserRepository.findByProviderIdAndProviderType(toCreate.getProviderId(), toCreate.getProvider());

        return created.get();
    }


}

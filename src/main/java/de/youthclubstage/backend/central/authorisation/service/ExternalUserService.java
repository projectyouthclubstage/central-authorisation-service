package de.youthclubstage.backend.central.authorisation.service;

import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.entity.Provider;
import de.youthclubstage.backend.central.authorisation.repository.ExternalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalUserService {

    private final ExternalUserRepository externalUserRepository;

    @Autowired
    public ExternalUserService(ExternalUserRepository externalUserRepository) {
        this.externalUserRepository = externalUserRepository;
    }


    public boolean createUser() {
        ExternalUser newUser = new ExternalUser();

        newUser.setProviderId("ID");
        newUser.setProviderType(Provider.FACEBOOK);
        newUser.setSystemAdmin(false);
        newUser.setUserId(1L);

        externalUserRepository.save(newUser);

        return true;
    }

    public Iterable<ExternalUser> getExternalUsers() {
        return externalUserRepository.findAll();
    }

}

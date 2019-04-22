package de.youthclubstage.backend.central.authorisation.repository;

import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.entity.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalUserRepository extends CrudRepository<ExternalUser, Long> {

    Optional<ExternalUser> findByProviderIdAndProviderType(String providerId, Provider provider);

    Optional<ExternalUser> findById(Long id);

}

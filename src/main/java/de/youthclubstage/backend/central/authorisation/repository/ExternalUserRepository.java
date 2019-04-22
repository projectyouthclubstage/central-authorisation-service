package de.youthclubstage.backend.central.authorisation.repository;

import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalUserRepository extends CrudRepository<ExternalUser, Long> {
}

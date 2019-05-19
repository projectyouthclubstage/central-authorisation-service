package de.youthclubstage.backend.central.authorisation.repository;

import de.youthclubstage.backend.central.authorisation.entity.UserGroupAssignment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserGroupAssignmentRepository extends CrudRepository<UserGroupAssignment, Long> {

    List<UserGroupAssignment> findAllByUserIdAndOrganisationId(Long userId, Long organisationId);

}

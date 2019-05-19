package de.youthclubstage.backend.central.authorisation.repository;

import de.youthclubstage.backend.central.authorisation.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    Optional<Member> findByUserIdAndOrganisationId(Long userId, Long organisationId);

}

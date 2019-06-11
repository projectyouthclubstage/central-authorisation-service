package de.youthclubstage.backend.central.authorisation.service

import de.youthclubstage.backend.central.authorisation.entity.ExternalUser
import de.youthclubstage.backend.central.authorisation.entity.Member
import de.youthclubstage.backend.central.authorisation.entity.Provider
import de.youthclubstage.backend.central.authorisation.entity.UserGroupAssignment
import de.youthclubstage.backend.central.authorisation.exception.TokenCreationException
import de.youthclubstage.backend.central.authorisation.exception.TokenValidationException
import de.youthclubstage.backend.central.authorisation.repository.MemberRepository
import de.youthclubstage.backend.central.authorisation.repository.UserGroupAssignmentRepository
import de.youthclubstage.backend.central.authorisation.security.TokenValidationService
import de.youthclubstage.backend.central.authorisation.service.model.GoogleData
import spock.lang.Specification
import spock.lang.Unroll

class ContextServiceTest extends Specification {

    ContextService contextService

    MemberRepository memberRepository = Mock()
    UserGroupAssignmentRepository userGroupAssignmentRepository = Mock()
    TokenValidationService tokenValidationService = Mock()
    TokenCreationService tokenCreationService = Mock()

    def setup() {
        contextService = new ContextService(
                memberRepository, userGroupAssignmentRepository, tokenValidationService, tokenCreationService)
    }


    @Unroll
    def "Get token for #name with a valid token"() {
        given:
        def member42 = new Member(42L, 1L, 4711L, false, false)
        memberRepository.findByUserIdAndOrganisationId(1L, 4711L) >> Optional.of(member42)
        def member43 = new Member(43L, 1L, 4712L, false, false)
        memberRepository.findByUserIdAndOrganisationId(1L, 4712L) >> Optional.of(member43)
        memberRepository.findByUserIdAndOrganisationId(1L, 4713L) >> Optional.empty()

        def groups = new ArrayList<UserGroupAssignment>()
        groups.add(new UserGroupAssignment(11L, 1L, 4711L, 11L))
        groups.add(new UserGroupAssignment(12L, 1L, 4711L, 12L))
        userGroupAssignmentRepository.findAllByUserIdAndOrganisationId(1L, 4711L) >> groups
        def emptyGroups = new ArrayList<UserGroupAssignment>()
        userGroupAssignmentRepository.findAllByUserIdAndOrganisationId(1L, 4712L) >> emptyGroups

        tokenValidationService.getUserId() >> userId

        tokenCreationService.generateToken(member42, groups) >> expectedResponse
        tokenCreationService.generateToken(member43, emptyGroups) >> expectedResponse


        when:
        def actual = contextService.getTokenForOrganisationId(organisationId)

        then:
        if(expectResult) {
            actual.isPresent()
            actual.get().getToken() == expectedResponse
        } else {
            !actual.isPresent()
        }

        where:
        name                                        | userId    | organisationId    | expectedResponse  | expectResult
        "an existing organisation with groups"      | 1L        | 4711L             | "success with"    | true
        "an existing organisation without groups"   | 1L        | 4712L             | "success without" | true
        "a non-existing organisation"               | 1L        | 4713L             | null              | false
    }

    @Unroll
    def "Get token for an organisation with an invalid token"() {
        given:
        tokenValidationService.getUserId() >> {throw new TokenValidationException(-1)}

        when:
        contextService.getTokenForOrganisationId(1L)

        then:
        thrown(TokenValidationException.class)
    }

    @Unroll
    def "Get #name with a valid token"() {
        given:
        def membershipList = new ArrayList<Member>()
        membershipList.add(new Member(42L, 1L, 4711L, false, false))
        membershipList.add(new Member(43L, 1L, 4712L, false, true))
        membershipList.add(new Member(44L, 1L, 4713L, true, false))
        membershipList.add(new Member(45L, 1L, 4714L, true, true))

        memberRepository.findAllByUserId(1L) >> new ArrayList<Member>()
        memberRepository.findAllByUserId(2L) >> membershipList

        tokenValidationService.getUserId() >> userId

        when:
        def actual = contextService.getOrganisations()

        then:
        actual.size() == expectedSize

        where:
        name                                | userId    | expectedSize
        "an empty list of organisations"    | 1L        | 0
        "a filled list of organisations"    | 2L        | 4
    }

    @Unroll
    def "Get organisations with an invalid token"() {
        given:
        tokenValidationService.getUserId() >> {throw new TokenValidationException(-1)}

        when:
        contextService.getOrganisations()

        then:
        thrown(TokenValidationException.class)
    }

    def "Token-generation throws an exception"() {
        given:
        def member = new Member(42L, 1L, 4711L, false, false)
        memberRepository.findByUserIdAndOrganisationId(1L, 4711L) >> Optional.of(member)

        def groups = new ArrayList<UserGroupAssignment>()
        groups.add(new UserGroupAssignment(11L, 1L, 4711L, 11L))
        groups.add(new UserGroupAssignment(12L, 1L, 4711L, 12L))
        userGroupAssignmentRepository.findAllByUserIdAndOrganisationId(1L, 4711L) >> groups

        tokenValidationService.getUserId() >> 1L

        tokenCreationService.generateToken(member, groups) >> {throw new ClassCastException("Any exception possible")}

        when:
        contextService.getTokenForOrganisationId(4711L)

        then:
        thrown(TokenCreationException.class)
    }

}

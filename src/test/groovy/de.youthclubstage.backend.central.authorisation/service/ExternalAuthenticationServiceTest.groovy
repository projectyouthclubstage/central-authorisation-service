package de.youthclubstage.backend.central.authorisation.service

import de.youthclubstage.backend.central.authorisation.entity.ExternalUser
import de.youthclubstage.backend.central.authorisation.entity.Provider
import de.youthclubstage.backend.central.authorisation.exception.ExternalAuthenticationFailedException
import de.youthclubstage.backend.central.authorisation.exception.TokenCreationException
import de.youthclubstage.backend.central.authorisation.repository.ExternalUserRepository
import de.youthclubstage.backend.central.authorisation.service.model.FacebookData
import de.youthclubstage.backend.central.authorisation.service.model.GoogleData
import de.youthclubstage.backend.central.authorisation.service.model.TokenInformation
import feign.FeignException
import spock.lang.Specification
import spock.lang.Unroll

class ExternalAuthenticationServiceTest extends Specification {

    ExternalAuthenticationService externalAuthenticationService

    GoogleService googleService = Mock()
    FacebookService facebookService = Mock()
    JwtTokenService jwtTokenService = Mock()
    ExternalUserRepository externalUserRepository = Mock()


    def setup() {
        externalAuthenticationService = new ExternalAuthenticationService(
                googleService, facebookService, jwtTokenService, externalUserRepository)
    }

    @Unroll
    def "Get token for #accessToken Facebook-Token (Response)"() {
        given:

        facebookService.getTokenInfoForToken("valid and existing", "id") >> new FacebookData("id-1")
        facebookService.getTokenInfoForToken("valid but not existing", "id") >>
                new FacebookData("id-2")

        externalUserRepository.findByProviderIdAndProviderType("id-1", Provider.FACEBOOK) >>
                Optional.of(new ExternalUser())
        externalUserRepository.findByProviderIdAndProviderType("id-2", Provider.FACEBOOK) >> Optional.empty()


        when:
        def actual = externalAuthenticationService.getTokenByFacebookLogin(accessToken)

        then:
        actual.isPresent() == isPresent

        where:
        accessToken                 | facebookId    | isPresent
        "valid and existing"        | "id-1"        | true
        "valid but not existing"    | "id-2"        | false
    }


    @Unroll
    def "Get token for #accessToken Facebook-Token (Exception)"() {
        given:

        facebookService.getTokenInfoForToken("invalid (id is null)", "id") >> new FacebookData()
        facebookService.getTokenInfoForToken("invalid (id is empty)", "id") >> new FacebookData("")
        facebookService.getTokenInfoForToken("invalid (Exception)", "id") >> {throw new FeignException("")}

        when:
        externalAuthenticationService.getTokenByFacebookLogin(accessToken)

        then:
        thrown (ExternalAuthenticationFailedException.class)

        where:
        accessToken             | errorCode
        "invalid (id is null)"  | "CAS#0022"
        "invalid (id is empty)" | "CAS#0022"
        "invalid (Exception)"   | "CAS#0021"
    }

    @Unroll
    def "Get token for #accessToken Google-Token (Response)"() {
        given:

        googleService.getTokenInfoForToken("valid and existing") >> new GoogleData("id-1")
        googleService.getTokenInfoForToken("valid but not existing") >> new GoogleData("id-2")

        externalUserRepository.findByProviderIdAndProviderType("id-1", Provider.GOOGLE) >>
                Optional.of(new ExternalUser())
        externalUserRepository.findByProviderIdAndProviderType("id-2", Provider.GOOGLE) >> Optional.empty()


        when:
        def actual = externalAuthenticationService.getTokenByGoogleLogin(accessToken)

        then:
        actual.isPresent() == isPresent

        where:
        accessToken                 | googleId  | isPresent
        "valid and existing"        | "id-1"    | true
        "valid but not existing"    | "id-2"    | false
    }


    @Unroll
    def "Get token for #accessToken Google-Token (Exception)"() {
        given:

        googleService.getTokenInfoForToken("invalid (id is null)") >> new GoogleData()
        googleService.getTokenInfoForToken("invalid (id is empty)") >> new GoogleData("")
        googleService.getTokenInfoForToken("invalid (Exception)") >> {throw new FeignException("")}

        when:
        externalAuthenticationService.getTokenByGoogleLogin(accessToken)

        then:
        thrown (ExternalAuthenticationFailedException.class)

        where:
        accessToken             | errorCode
        "invalid (id is null)"  | "CAS#0012"
        "invalid (id is empty)" | "CAS#0012"
        "invalid (Exception)"   | "CAS#0011"
    }

    def "Token-generation throws an exception"() {
        given:

        def idToken = "idToken"
        def googleId = "externalId"
        def userId = 4711L
        def externalUser = new ExternalUser(userId, googleId, Provider.GOOGLE, true)
        def tokenInformation = new TokenInformation(userId, true, null, false, false, new ArrayList<Long>())

        googleService.getTokenInfoForToken(idToken) >> new GoogleData(googleId)
        externalUserRepository.findByProviderIdAndProviderType(googleId, Provider.GOOGLE) >> Optional.of(externalUser)
        jwtTokenService.generateToken(tokenInformation) >> {throw new ClassCastException("Any exception possible")}

        when:
        externalAuthenticationService.getTokenByGoogleLogin(idToken)

        then:
        thrown(TokenCreationException.class)
    }

}

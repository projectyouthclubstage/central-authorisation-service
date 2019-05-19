package de.youthclubstage.backend.central.authorisation.service.mapper;

import de.youthclubstage.backend.central.authorisation.service.model.TokenInformation;
import de.youthclubstage.backend.central.authorisation.entity.ExternalUser;
import de.youthclubstage.backend.central.authorisation.entity.Member;
import de.youthclubstage.backend.central.authorisation.entity.UserGroupAssignment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenInformationMapper {

    public static Optional<TokenInformation> toTokenInformation
            (ExternalUser user, Member member, List<UserGroupAssignment> groups) {

        Optional<TokenInformation> subMapper = toTokenInformation(user);

        if (!subMapper.isPresent()) {
            return Optional.empty();
        }

        TokenInformation retValue = subMapper.get();

        retValue.setOrganisationId(member.getOrganisationId());
        retValue.setOrganisationModerator(member.getModerator());
        retValue.setOrganisationAdministrator(member.getAdministrator());

        for(UserGroupAssignment current : groups) {
            retValue.getGroups().add(current.getGroupId());
        }

        return Optional.of(retValue);
    }

    public static Optional<TokenInformation> toTokenInformation(ExternalUser user) {

        TokenInformation retValue = new TokenInformation();

        retValue.setUserId(user.getUserId());
        retValue.setSystemAdministrator(user.getSystemAdmin());

        return Optional.of(retValue);
    }
}

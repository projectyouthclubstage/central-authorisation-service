package de.youthclubstage.backend.central.authorisation.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("UserGroupAssignment")
@Data
public class UserGroupAssignment {

    @Id
    private Long id;

    @Indexed
    private Long userId;

    @Indexed
    private Long organisationId;

    @Indexed
    private Long groupId;

}

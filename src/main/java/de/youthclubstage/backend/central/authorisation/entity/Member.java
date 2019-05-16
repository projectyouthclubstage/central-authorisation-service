package de.youthclubstage.backend.central.authorisation.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("Member")
@Data
public class Member {

    @Id
    private Long id;

    @Indexed
    private Long userId;

    @Indexed
    private Long organisationId;

    private Boolean administrator = false;

    private Boolean moderator = false;

}

package de.youthclubstage.backend.central.authorisation.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash("ExternalUser")
@Data
public class ExternalUser {

    @Id
    private Long userId;

    @Indexed
    private String providerId;

    @Indexed
    private Provider providerType;

    private Boolean systemAdmin;

}

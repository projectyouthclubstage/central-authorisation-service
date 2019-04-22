package de.youthclubstage.backend.central.authorisation.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("ExternalUser")
@Data
public class ExternalUser {

    @Id
    private Long userId;

    private String providerId;

    private Provider providerType;

    private Boolean systemAdmin;

}

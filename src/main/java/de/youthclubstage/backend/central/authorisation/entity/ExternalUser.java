package de.youthclubstage.backend.central.authorisation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash("ExternalUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalUser {

    @Id
    private Long userId;

    @Indexed
    private String providerId;

    @Indexed
    private Provider providerType;

    private Boolean systemAdmin;

}

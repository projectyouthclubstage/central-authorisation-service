package de.youthclubstage.backend.central.authorisation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash("UserGroupAssignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

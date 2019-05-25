package de.youthclubstage.backend.central.authorisation.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@Data
@ConfigurationProperties(value = "redis")
public class RedisConfigurationProperies {
    private String server;
    private Integer port;
}

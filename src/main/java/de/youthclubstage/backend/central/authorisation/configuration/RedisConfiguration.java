package de.youthclubstage.backend.central.authorisation.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
@ComponentScan("de.youthclubstage.backend.central.authorisation")
@EnableRedisRepositories(basePackages = "de.youthclubstage.backend.central.authorisation")
@PropertySource("classpath:application.yml")
public class RedisConfiguration {

    private final RedisConfigurationProperies redisConfigurationProperies;

    @Autowired
    public RedisConfiguration(RedisConfigurationProperies redisConfigurationProperies){
        this.redisConfigurationProperies = redisConfigurationProperies;
    }


    @Bean
    public JedisConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration config =
                new RedisStandaloneConfiguration(redisConfigurationProperies.getServer(),
                        redisConfigurationProperies.getPort());
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

}
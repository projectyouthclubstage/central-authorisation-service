package de.youthclubstage.backend.central.authorisation.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    private final CorsConfigurationProperties corsConfigurationProperties;

    @Autowired
    public CorsConfiguration(CorsConfigurationProperties corsConfigurationProperties){
        this.corsConfigurationProperties = corsConfigurationProperties;
    }


    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsConfigurationProperties.getAllowedOrigins())
                .allowedHeaders(corsConfigurationProperties.getAllowedHeaders())
                .exposedHeaders(corsConfigurationProperties.getExposedHeaders())
                .allowedMethods("GET","PUT","POST","DELETE");
    }


}
package com.carter.surveyorbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Configuration file used for setting the global security policies.
 */
@Configuration
public class SecurityConfig {

    @Value("#{'${surveyor.security.cors.allow-origins}'.split(',')}")
    private List<String> corsAllowedOrigins;

    @Value("#{'${surveyor.security.cors.allow-headers}'.split(',')}")
    private List<String> corsAllowedHeaders;

    /**
     * Configuration bean used for setting the global Cross Origin Resource Sharing (CORS) policy,
     * in order to allow access from the frontend service.
     * @return A Spring CorsConfigurationSource bean.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsAllowedOrigins);
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        corsAllowedHeaders.forEach(configuration::addAllowedHeader);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configuration bean used for setting the global Cross Origin Resource Sharing (CORS) filter.
     * @param corsConfigurationSource A CORS configuration source with a specified policy.
     * @return A Spring CorsFilter bean.
     */
    @Bean
    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource) {
        return new CorsFilter(corsConfigurationSource);
    }
}

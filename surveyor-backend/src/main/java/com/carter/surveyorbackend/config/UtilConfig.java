package com.carter.surveyorbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Spring Configuration file used for declaring utility beans.
 */
@Configuration
public class UtilConfig {

    /**
     * Creates a clock bean for use when timestamping new database records.
     * @return A Clock bean.
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

}

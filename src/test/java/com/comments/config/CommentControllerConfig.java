package com.comments.config;

import com.comments.service.LocationService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Created by joshua on 2016-07-18.
 */

@Profile("test")
@Configuration
public class CommentControllerConfig {
    @Bean
    @Primary
    public LocationService locationService() {
        return Mockito.mock(LocationService.class);
    }
}



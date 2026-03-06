package com.citymate.community.configuration;

import com.citymate.community.exception.GlobalExceptionMapper;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;
import org.glassfish.jersey.server.ResourceConfig;


@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.citymate.community.controller");
        register(GlobalExceptionMapper.class);
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }
}

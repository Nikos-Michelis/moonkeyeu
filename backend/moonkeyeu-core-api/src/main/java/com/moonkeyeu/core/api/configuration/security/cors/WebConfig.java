package com.moonkeyeu.core.api.configuration.security.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${server.cors.origins}")
    private List<String> allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins.toArray(new String[0]))
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("Authorization", "Content-Type", "X-XSRF-TOKEN", "User-Agent")
                        .allowCredentials(true)
                        .maxAge(3600);

                registry.addMapping("/api/v1/public/**")
                        .allowedOrigins(allowedOrigins.toArray(new String[0]))
                        .allowedMethods("GET", "OPTIONS")
                        .allowedHeaders("Authorization", "Content-Type", "User-Agent")
                        .allowCredentials(false)
                        .maxAge(3600);

                registry.addMapping("/api/v1/images/**")
                        .allowedOrigins(allowedOrigins.toArray(new String[0]))
                        .allowedMethods("GET", "OPTIONS")
                        .allowedHeaders("Authorization", "Content-Type", "User-Agent")
                        .allowCredentials(false);
            }
        };
    }
}


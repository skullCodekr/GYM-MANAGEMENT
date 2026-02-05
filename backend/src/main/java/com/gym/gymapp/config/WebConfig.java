package com.gym.gymapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsAndFallbackConfigurer() {
        return new WebMvcConfigurer() {

            // Preserve CORS settings
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")       // all endpoints
                        .allowedOrigins("http://localhost:3000") // React default port
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }

            // Add React Router fallback for unknown routes
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/{spring:\\w+}")
                        .setViewName("forward:/index.html");
                registry.addViewController("/**/{spring:\\w+}")
                        .setViewName("forward:/index.html");
                registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
                        .setViewName("forward:/index.html");
            }
        };
    }
}

package org.sid.ebankingbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // السماح لكل المسارات
                        .allowedOrigins("*") // السماح لأي دومين
                        .allowedMethods("*") // السماح لأي ميثود
                        .allowedHeaders("*"); // السماح لأي هيدر
                // .allowCredentials(false); // يمكن تفعيلها إذا أردت السماح بالكريدنشال
            }
        };
    }
}

package dev.boki.flowassignment.config;

import static org.springframework.http.HttpMethod.*;

import dev.boki.flowassignment.converter.UpperConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UpperConverter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods(OPTIONS.name(), DELETE.name(), PATCH.name(), PUT.name(), HEAD.name(),
                POST.name(), GET.name());
    }
}
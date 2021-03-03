package com.example.vstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("file:C:\\Users\\MININT-IAEC8I7-local\\IdeaProjects\\v-store\\src\\main\\resources\\")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver());

         */

        registry.addResourceHandler("/avt/**")
                .addResourceLocations("file:C:\\Users\\MININT-IAEC8I7-local\\IdeaProjects\\v-store\\src\\main\\resources\\static\\avt\\")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver());


    }

}


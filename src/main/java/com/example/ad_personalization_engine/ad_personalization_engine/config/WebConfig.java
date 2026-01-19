package com.example.ad_personalization_engine.ad_personalization_engine.config;

import com.example.ad_personalization_engine.ad_personalization_engine.interceptor.RateLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestLoggingInterceptor requestLoggingInterceptor;
    private final RateLimitProperties rateLimitProperties;


    public WebConfig(RateLimitProperties rateLimitProperties) {
        this.requestLoggingInterceptor = new RequestLoggingInterceptor();
        this.rateLimitProperties = rateLimitProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLoggingInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(
                new RateLimitInterceptor(rateLimitProperties)
        ).addPathPatterns("/**");
    }
}

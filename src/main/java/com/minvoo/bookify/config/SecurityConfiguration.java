package com.minvoo.bookify.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Disable Cross Site Request Forgery
        http.csrf().disable();

        // Protect endpoints at /api/<type>/secure
        http.authorizeRequests(configurer ->
                configurer.antMatchers("/api/books/secure/**",
                        "/api/reviews/secure/**").authenticated())
                .oauth2ResourceServer().jwt();

        //Add cors filter to API endpoints
        http.cors();

        //Add content to negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        //Force okta a non-empty response body for 401 to make the response friendly
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }
}

package com.egconley.Auth0TechnicalExercise;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
@Configuration
@PropertySource("classpath:auth0.properties")
public class AppConfig {
    /**
     * This is your auth0 domain (tenant you have created when registering with auth0 - account name)
     */
    @Value(value = "${com.auth0.domain}")
    private String domain;

    /**
     * This is the client id of your auth0 application (see Settings page on auth0 dashboard)
     */
    @Value(value = "${com.auth0.ruleTrackerClientId}")
    private String clientId;

    /**
     * This is the client secret of your auth0 application (see Settings page on auth0 dashboard)
     */
    @Value(value = "${com.auth0.ruleTrackerClientSecret}")
    private String clientSecret;

    /**
     * This is the client id for the Management API/v2
     */
    @Value(value = "${com.auth0.APIClientID}")
    private String apiclientId;

    public static String apiclientId_STATIC;

    public void setAPIClientID(String apiclientId) {
        AppConfig.apiclientId_STATIC = apiclientId;
    }

    /**
     * This is the client secret for the Management API/v2
     */
    @Value(value = "${com.auth0.APIClientSecret}")
    private String apiclientSecret;

    public static String apiclientSecret_STATIC;

    public void setAPIClientSecret(String apiclientSecret) {
        AppConfig.apiclientSecret_STATIC = apiclientSecret;
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new Auth0Filter());
        registration.addUrlPatterns("/portal/*");
        registration.setName(Auth0Filter.class.getSimpleName());
        return registration;
    }

    public String getDomain() {
        return domain;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public static String getApiclientId() {
        return AppConfig.apiclientId_STATIC;
    }

    public static String getApiclientSecret() {
        return AppConfig.apiclientSecret_STATIC;
    }
}

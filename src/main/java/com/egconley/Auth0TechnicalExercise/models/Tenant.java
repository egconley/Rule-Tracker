package com.egconley.Auth0TechnicalExercise.models;

public class Tenant {
    private String tenant;
    private String domain;
    private String apiClientID;
    private String apiClientSecret;

    public String getTenant() {
        return tenant;
    }

    public String getTenantDomain() {
        return domain;
    }

    public String getApiClientID() {
        return apiClientID;
    }

    public String getApiClientSecret() {
        return apiClientSecret;
    }

    public Tenant(String tenant, String clientID, String clientSecret) {
        this.tenant = tenant;
        this.domain = "https://" + tenant + ".auth0.com";
        this.apiClientID = clientID;
        this.apiClientSecret = clientSecret;
    }
}

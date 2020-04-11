package com.egconley.Auth0TechnicalExercise.models;

public class Tenant {
    private String tenant;
    private String domain;

    public String getTenant() {
        return tenant;
    }

    public String getTenantDomain() {
        return domain;
    }

    public Tenant(String tenant) {
        this.tenant = tenant;
        this.domain = "https://" + tenant + ".auth0.com";
    }
}

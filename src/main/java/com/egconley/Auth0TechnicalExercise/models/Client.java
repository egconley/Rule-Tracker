package com.egconley.Auth0TechnicalExercise.models;

public class Client {

    String client_id;
    String tenant;
    String name;

    public String getClient_id() {
        return client_id;
    }

    public String getTenant() {
        return tenant;
    }

    public String getName() {
        return name;
    }

    public Client(String client_id, String tenant, String name) {
        this.client_id = client_id;
        this.tenant = tenant;
        this.name = name;
    }
}

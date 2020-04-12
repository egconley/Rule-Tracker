package com.egconley.Auth0TechnicalExercise.models;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;

public class TenantClients {

    public Client[] getTenantClients(HttpResponse<String> response) {

        Client[] clients = new Gson().fromJson(response.getBody(), Client[].class);

        return clients;
    }
}
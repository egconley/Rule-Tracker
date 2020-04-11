package com.egconley.Auth0TechnicalExercise.ManagementAPI;

import com.egconley.Auth0TechnicalExercise.models.Tenant;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class APIConnection {

    public static HttpResponse<String> getAPIData(Tenant tenant, String endpoint) {

        String tenantDomain = tenant.getTenantDomain();
        AccessToken token;
        String audience = tenantDomain + "/api/v2/";
        HttpResponse<String> apiResponse = null;

        // Auth0 Management API Connection
        try {
            // set request body content
            StringBuilder builder = new StringBuilder();
            // TODO: App User: hard code in client_id and client_secret values
            builder.append("{\"client_id\":\"").append(System.getenv("CLIENT_ID"))
                    .append("\",\"client_secret\":\"").append(System.getenv("CLIENT_SECRET"))
                    .append("\",\"audience\":\"").append(audience)
                    .append("\",\"grant_type\":\"client_credentials\"}");
            String bodyContent = builder.toString();

            // get token
            HttpResponse<String> tokenResponse = Unirest.post(tenantDomain + "/oauth/token")
                    .header("content-type", "application/json")
                    .body(bodyContent)
                    .asString();
            token = new Gson().fromJson(tokenResponse.getBody(), AccessToken.class);

            // set value for authorization key request header
            String tokeTypeAndAccessToken = token.token_type + " " + token.access_token;

            // get list of rules for tenant from Managment API
            apiResponse = Unirest.get(audience + endpoint)
                    .header("authorization", tokeTypeAndAccessToken)
                    .asString();

        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return apiResponse;
    }
}

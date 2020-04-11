package com.egconley.Auth0TechnicalExercise.ManagementAPI;

public class AccessToken {
    String token_type;
    String access_token;

    public AccessToken(String access_token, String token_type) {
        this.access_token = access_token;
        this.token_type = token_type;
    }
}

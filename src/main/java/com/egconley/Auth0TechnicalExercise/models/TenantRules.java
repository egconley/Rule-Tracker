package com.egconley.Auth0TechnicalExercise.models;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;

public class TenantRules {

    public Rule[] getTenantRules(HttpResponse<String> response) {

        Rule[] rules = new Gson().fromJson(response.getBody(), Rule[].class);

        System.out.println("!!!!!!!!!" + rules.toString());
        return rules;
    }
}

package com.egconley.Auth0TechnicalExercise.models;

import com.egconley.Auth0TechnicalExercise.ManagementAPI.APIConnection;
import com.mashape.unirest.http.HttpResponse;

import java.util.HashMap;
import java.util.List;

public class AllClientRules {

    public static HashMap<String, List<String>>  getAllClientRules(List<String> tenantNames) {
        HashMap<String, List<String>> rulesByApp = new HashMap<>();

        for (String tenant : tenantNames) {
            HttpResponse<String> rulesResponse = APIConnection.getAPIData(new Tenant(tenant), "rules");
            Rule[] rules = TenantRules.getTenantRules(rulesResponse);

            HttpResponse<String> clientsResponse = APIConnection.getAPIData(new Tenant(tenant), "clients");
            Client[] clients = TenantClients.getTenantClients(clientsResponse);
            HashMap<String, List<String>> rulesByTenantApp = TenantClientRules.getTenantClientRules(clients, rules);
            rulesByApp.putAll(rulesByTenantApp);
        }

        return rulesByApp;
    }
}

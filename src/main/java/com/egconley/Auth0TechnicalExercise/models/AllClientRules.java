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
            HttpResponse<String> clientsResponse = APIConnection.getAPIData(new Tenant(tenant), "clients");

            if (clientsResponse.getStatus()==200 && rulesResponse.getStatus()==200) {
                Rule[] rules = TenantRules.getTenantRules(rulesResponse);
                Client[] clients = TenantClients.getTenantClients(clientsResponse);
                HashMap<String, List<String>> rulesByTenantApp = TenantClientRules.getTenantClientRules(clients, rules);
                rulesByApp.putAll(rulesByTenantApp);
            } else {
                System.out.println("Status: " + clientsResponse.getStatus() + " " + clientsResponse.getStatusText());
                break;
            }
        }
        return rulesByApp;
    }
}

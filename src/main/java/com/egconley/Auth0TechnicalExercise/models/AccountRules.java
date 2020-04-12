package com.egconley.Auth0TechnicalExercise.models;

import com.egconley.Auth0TechnicalExercise.ManagementAPI.APIConnection;
import com.mashape.unirest.http.HttpResponse;

import java.util.HashMap;
import java.util.List;

public class AccountRules {

    public HashMap<String, List<String>>  getAllClientRules(List<String> tenantNames, String apiclientId, String apiclientSecret) {
        HashMap<String, List<String>> rulesByApp = new HashMap<>();
        APIConnection connection = new APIConnection();
        TenantRules tenantRules = new TenantRules();
        TenantClients tenantClients = new TenantClients();
        TenantClientRules tenantClientRules = new TenantClientRules();

        for (String tenant : tenantNames) {
            HttpResponse<String> rulesResponse = connection.getAPIData(new Tenant(tenant), "rules", apiclientId, apiclientSecret);
            HttpResponse<String> clientsResponse = connection.getAPIData(new Tenant(tenant), "clients", apiclientId, apiclientSecret);

            if (clientsResponse.getStatus()==200 && rulesResponse.getStatus()==200) {
                Rule[] rules = tenantRules.getTenantRules(rulesResponse);
                Client[] clients = tenantClients.getTenantClients(clientsResponse);
                HashMap<String, List<String>> rulesByTenantApp = tenantClientRules.getTenantClientRules(clients, rules);
                rulesByApp.putAll(rulesByTenantApp);
            } else {
                System.out.println("Status: " + clientsResponse.getStatus() + " " + clientsResponse.getStatusText());
                break;
            }
        }
        return rulesByApp;
    }
}

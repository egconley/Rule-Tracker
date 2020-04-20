package com.egconley.Auth0TechnicalExercise.models;

import com.egconley.Auth0TechnicalExercise.ManagementAPI.APIConnection;
import com.mashape.unirest.http.HttpResponse;

import java.util.HashMap;
import java.util.List;

public class AccountRules {

    public HashMap<String, List<String>>  getAllClientRules(List<Tenant> tenants) {
        HashMap<String, List<String>> rulesByApp = new HashMap<>();
        APIConnection connection = new APIConnection();
        TenantRules tenantRules = new TenantRules();
        TenantClients tenantClients = new TenantClients();
        TenantClientRules tenantClientRules = new TenantClientRules();

        for (Tenant tenant : tenants) {
            HttpResponse<String> rulesResponse = connection.getAPIData(tenant, "rules", tenant.getApiClientID(), tenant.getApiClientSecret());
            HttpResponse<String> clientsResponse = connection.getAPIData(tenant, "clients", tenant.getApiClientID(), tenant.getApiClientSecret());

            if (clientsResponse.getStatus()==200 && rulesResponse.getStatus()==200) {
                Rule[] rules = tenantRules.getTenantRules(rulesResponse);
                Client[] clients = tenantClients.getTenantClients(clientsResponse);
                HashMap<String, List<String>> rulesByTenantApp = tenantClientRules.getTenantClientRules(clients, rules, tenant);
                rulesByApp.putAll(rulesByTenantApp);
            } else {
                System.out.println("Status: " + clientsResponse.getStatus() + " " + clientsResponse.getStatusText());
                break;
            }
        }
        return rulesByApp;
    }
}

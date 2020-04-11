package com.egconley.Auth0TechnicalExercise.ManagementAPI;

import com.egconley.Auth0TechnicalExercise.models.*;
import com.mashape.unirest.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class APIConnectionTest {

    String tenantName;
    Tenant tenant;

    String clientEndpoint;
    HttpResponse<String> clientResponse;
    Client[] clients;
    HashSet<String> clientNames;

    String rulesEndpoint;
    HttpResponse<String> rulesResponse;
    Rule[] rules;
    HashSet<String> ruleNames;

    @BeforeEach
    void setUp() {
        // TODO: com.egconley.Auth0DSETechExercise.App User should change tenantName value to one of their own tenants
        tenantName = "dev-s4sen828";
        tenant = new Tenant(tenantName);

        clientEndpoint = "clients";
        clientResponse = APIConnection.getAPIData(tenant, clientEndpoint);

        clients = TenantClients.getTenantClients(clientResponse);
        clientNames = new HashSet<>();
        for (Client client : clients) {
            clientNames.add(client.getName());
        }

        rulesEndpoint = "rules";
        rulesResponse = APIConnection.getAPIData(tenant, rulesEndpoint);

        rules = TenantRules.getTenantRules(rulesResponse);
        ruleNames = new HashSet<>();
        for (Rule rule : rules) {
            ruleNames.add(rule.getName());
        }
    }

    @Test
    void getAPIData() {
    }

    @Test
    void getClientsFromAPI() {
        // TODO: com.egconley.Auth0DSETechExercise.App user should change app names to ones that exist in the tenant above
        assertTrue(clientNames.contains("Fruit Bot"));
    }

    @Test
    void getRulessFromAPI() {
        // TODO: com.egconley.Auth0DSETechExercise.App User should change "Add country to the user profile" to the name of one of their own rules
        assertTrue(ruleNames.contains("Allow Access during weekdays for a Fruit Bot"));
    }
}
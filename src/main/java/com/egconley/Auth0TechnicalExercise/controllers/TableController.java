package com.egconley.Auth0TechnicalExercise.controllers;

import com.egconley.Auth0TechnicalExercise.models.AccountRules;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
@Component
@Configuration
@PropertySource("classpath:auth0.properties")
@org.springframework.stereotype.Controller
public class TableController {

    // TODO: option: add additional tenants
    @Value(value = "${com.auth0.tenant}")
    private String tenantName;

    @Value(value = "${com.auth0.APIClientID}")
    private String apiclientId;

    /**
     * This is the client secret for the Management API/v2
     */
    @Value(value = "${com.auth0.APIClientSecret}")
    private String apiclientSecret;

    @GetMapping("/")
    public String index(Model m) {
		List<String> tenantNames = new LinkedList<>();
        // TODO: option: add additional tenants
		tenantNames.add(tenantName);
        AccountRules rules = new AccountRules();
		HashMap<String, List<String>> rulesByApp = rules.getAllClientRules(tenantNames, apiclientId, apiclientSecret);
		m.addAttribute("data", rulesByApp);
        return "home";
    }
}

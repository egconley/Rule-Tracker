package com.egconley.Auth0TechnicalExercise;

import com.egconley.Auth0TechnicalExercise.models.AllClientRules;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Component
@Configuration
@PropertySource("classpath:auth0.properties")
@org.springframework.stereotype.Controller
public class Controller {

    @Value(value = "${com.auth0.APIClientID}")
    private String apiclientId;

    /**
     * This is the client secret for the Management API/v2
     */
    @Value(value = "${com.auth0.APIClientSecret}")
    private String apiclientSecret;

    @GetMapping("/")
    public String index(Model m) {
        // TODO: App User hard code in own tenant name
        String tenantName = "dev-s4sen828";
		List<String> tenantNames = new LinkedList<>();
		tenantNames.add(tenantName);
        AllClientRules rules = new AllClientRules();
		HashMap<String, List<String>> rulesByApp = rules.getAllClientRules(tenantNames, apiclientId, apiclientSecret);
		m.addAttribute("data", rulesByApp);
        return "home";
    }
}

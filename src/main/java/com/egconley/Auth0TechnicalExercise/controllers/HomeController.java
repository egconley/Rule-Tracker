package com.egconley.Auth0TechnicalExercise.controllers;

import com.auth0.SessionUtils;
import com.egconley.Auth0TechnicalExercise.models.AccountRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Component
@Configuration
@PropertySource("classpath:auth0.properties")
@Controller
public class HomeController {

    // TODO: (optional) add additional tenants
    @Value(value = "${com.auth0.tenant}")
    private String tenantName;

    @Value(value = "${com.auth0.APIClientID}")
    private String apiclientId;

    /**
     * This is the client secret for the Management API/v2
     */
    @Value(value = "${com.auth0.APIClientSecret}")
    private String apiclientSecret;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/portal/home")
    protected String home(final Map<String, Object> model, final HttpServletRequest req, Model m) {
        logger.info("Home page");
        String accessToken = (String) SessionUtils.get(req, "accessToken");
        String idToken = (String) SessionUtils.get(req, "idToken");
        if (accessToken != null) {
            model.put("userId", accessToken);
        } else if (idToken != null) {
            model.put("userId", idToken);
        }

        List<String> tenantNames = new LinkedList<>();
        // TODO: (optional) add additional tenants
        tenantNames.add(tenantName);
        AccountRules rules = new AccountRules();
        HashMap<String, List<String>> rulesByApp = rules.getAllClientRules(tenantNames, apiclientId, apiclientSecret);
        m.addAttribute("data", rulesByApp);

        return "table";
    }

}
package com.egconley.Auth0TechnicalExercise.controllers;

import com.auth0.SessionUtils;
import com.egconley.Auth0TechnicalExercise.models.AccountRules;
import com.egconley.Auth0TechnicalExercise.models.Tenant;
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

    @Value(value = "${com.auth0.tenant1}")
    private String tenant1Name;

    @Value(value = "${com.auth0.tenant1.APIClientID}")
    private String t1apiclientId;

    @Value(value = "${com.auth0.tenant1.APIClientSecret}")
    private String t1apiclientSecret;

    @Value(value = "${com.auth0.tenant2}")
    private String tenant2Name;

    @Value(value = "${com.auth0.tenant2.APIClientID}")
    private String t2apiclientId;

    @Value(value = "${com.auth0.tenant2.APIClientSecret}")
    private String t2apiclientSecret;

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

        List<Tenant> tenants = new LinkedList<>();
        tenants.add(new Tenant(tenant1Name, t1apiclientId, t1apiclientSecret));
        tenants.add(new Tenant(tenant2Name, t2apiclientId, t2apiclientSecret));
        AccountRules rules = new AccountRules();
        HashMap<String, List<String>> rulesByApp = rules.getAllClientRules(tenants);
        m.addAttribute("data", rulesByApp);

        return "table";
    }

}
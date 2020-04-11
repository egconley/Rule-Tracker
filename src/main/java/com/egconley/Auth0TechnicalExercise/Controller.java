package com.egconley.Auth0TechnicalExercise;

import com.egconley.Auth0TechnicalExercise.models.AllClientRules;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/")
    public String index(Model m) {
        String tenantName = "dev-s4sen828";
		List<String> tenantNames = new LinkedList<>();
		tenantNames.add(tenantName);
		HashMap<String, List<String>> rulesByApp = AllClientRules.getAllClientRules(tenantNames);
        for (Map.Entry<String, List<String>> entry : rulesByApp.entrySet()) {
            System.out.println(entry.getKey());
        }
		m.addAttribute("data", rulesByApp);
        return "home";
    }
}

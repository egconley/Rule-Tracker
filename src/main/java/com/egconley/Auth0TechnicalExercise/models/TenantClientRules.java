package com.egconley.Auth0TechnicalExercise.models;

import com.egconley.Auth0TechnicalExercise.utils.RuleScriptScanner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TenantClientRules {

    public HashMap<String, List<String>>  getTenantClientRules(Client[] clients, Rule[] rules, Tenant tenant) {

        RuleScriptScanner scanner = new RuleScriptScanner();
        HashMap<String, List<String>> appRuleListMap = new HashMap<>();
        for (Client client : clients) {
            appRuleListMap.put(client.getName(), new LinkedList<String>());
        }
        appRuleListMap.remove("All Applications");
        appRuleListMap.put("All " + tenant.getTenant() + " Applications", new LinkedList<String>());

        for (Rule rule : rules) {
            List<String> appNames = scanner.getAppNames(rule.getScript(), clients);
            // if rule script doesn't include specific apps, assume it applies to all applications
            if (appNames.isEmpty()) {
                List<String> appRules = appRuleListMap.get("All " + tenant.getTenant() + " Applications");
                appRules.add(rule.getName());
                appRuleListMap.put("All " + tenant.getTenant() + " Applications", appRules);
            } else {
                // if rules script includes specific apps, file the rule under each of the apps named
                for (String name : appNames) {
                    List<String> appRules = appRuleListMap.get(name);
                    appRules.add(rule.getName());
                    appRuleListMap.put(name, appRules);
                }
            }
        }

        return appRuleListMap;
    }
}
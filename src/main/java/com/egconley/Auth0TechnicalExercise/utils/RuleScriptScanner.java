package com.egconley.Auth0TechnicalExercise.utils;

import com.egconley.Auth0TechnicalExercise.models.Client;

import java.util.*;

public class RuleScriptScanner {

    public static List<String> getAppNames(String script, Client[] clients) {

        Stack<Character> quotes = new Stack<>();

        StringBuilder builder = new StringBuilder();
        List<String> ruleAppNames = new LinkedList<>();

        HashSet<String> appNames = new HashSet<>();
        for (Client client : clients) {
            appNames.add(client.getName());
        }

        HashSet<String> appIDs = new HashSet<>();
        for (Client client : clients) {
            appIDs.add(client.getClient_id());
        }

        HashMap<String, String> appIDsAndNames = new HashMap<>();
        for (Client client : clients) {
            appIDsAndNames.put(client.getClient_id(), client.getName());
        }

        for (int i=0; i<script.length(); i++) {

            char current = script.charAt(i);
            boolean startOfPossibleAppName = quotes.isEmpty() && current=='\'';
            boolean partofPossibleAppName = !quotes.isEmpty() && current!='\'';
            boolean endofPossibleAppName = !quotes.isEmpty() && current=='\'';

            if (startOfPossibleAppName) {
                quotes.push(script.charAt(i));
            }
            if (partofPossibleAppName) {
                builder.append(script.charAt(i));
            }
            if (endofPossibleAppName) {
                // empty quotes stack
                quotes.clear();
                // save builder to string
                String possibleAppName = builder.toString();
                // check to see if the string is a client_name
                if (appNames.contains(possibleAppName)) {
                    ruleAppNames.add(possibleAppName);
                    builder = new StringBuilder();
                    // if string isn't a client name, get to see if it's a client_id
                } else if (appIDs.contains(possibleAppName)) {
                    // if so, get the app name and add it to ruleAppNames
                    String appName = appIDsAndNames.get(possibleAppName);
                    ruleAppNames.add(appName);
                    builder = new StringBuilder();
                } else {
                    // string isn't a client_id or client name, clear builder
                    builder = new StringBuilder();
                }
            }
        }
        return ruleAppNames;
    }
}
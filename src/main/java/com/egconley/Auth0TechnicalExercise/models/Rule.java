package com.egconley.Auth0TechnicalExercise.models;

public class Rule {

    String id;
    boolean enabled;
    String script;
    String name;
    int order;
    String stage;

    public String getId() {
        return id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getScript() {
        return script;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public String getStage() {
        return stage;
    }

    public Rule(String id, boolean enabled, String script, String name, int order, String stage) {
        this.id = id;
        this.enabled = enabled;
        this.script = script;
        this.name = name;
        this.order = order;
        this.stage = stage;
    }
}

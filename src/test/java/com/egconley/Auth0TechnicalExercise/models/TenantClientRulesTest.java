package com.egconley.Auth0TechnicalExercise.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TenantClientRulesTest {

    Client[] clients;
    String testScript1AppName;
    String testScript3ClientIDs;
    String testScriptNoAppReferences;
    Rule[] rules;

    @BeforeEach
    void setUp() {
        clients = new Client[]{new Client("mkrG83lsKIOadGrWTS6fXb3QXASUABSn", "test tenant", "Fruit Bot"),
                new Client("KyFlLCELqa8flAt47exfXo5qz8GT99Rv", "test tenant", "Byte Vegetables"),
                new Client("KLakvhTX67hRwXeAV7pBl3F8ZG4UD6tx", "test tenant", "Fruitprism"),
                new Client("1234", "test tenant", "All Applications")};

        testScript1AppName = "function accessOnWeekdaysOnly(user, context, callback) {\n" +
                "\n" +
                "  if (context.clientName === 'Fruit Bot') {\n" +
                "    const date = new Date();\n" +
                "    const d = date.getDay();\n" +
                "\n" +
                "    if (d === 0 || d === 6) {\n" +
                "      return callback(new UnauthorizedError('Fruit Bot is available during the week'));\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  callback(null, user, context);\n" +
                "}";
        testScript3ClientIDs = "function disableSocialSignups(user, context, callback) {\n" +
                "\n" +
                "  const CLIENTS_ENABLED = ['mkrG83lsKIOadGrWTS6fXb3QXASUABSn', 'KyFlLCELqa8flAt47exfXo5qz8GT99Rv', 'KLakvhTX67hRwXeAV7pBl3F8ZG4UD6tx'];\n" +
                "  // run only for the specified clients\n" +
                "  if (CLIENTS_ENABLED.indexOf(context.clientID) === -1) {\n" +
                "    return callback(null, user, context);\n" +
                "  }\n" +
                "\n" +
                "  // initialize app_metadata\n" +
                "  user.app_metadata = user.app_metadata || {};\n" +
                "\n" +
                "  const is_social = context.connectionStrategy === context.connection;\n" +
                "  // if it is the first login (hence the `signup`) and it is a social login\n" +
                "  if (context.stats.loginsCount === 1 && is_social) {\n" +
                "\n" +
                "    // turn on the flag\n" +
                "    user.app_metadata.is_signup = true;\n" +
                "\n" +
                "    // store the app_metadata\n" +
                "    auth0.users.updateAppMetadata(user.user_id, user.app_metadata)\n" +
                "      .then(function(){\n" +
                "        // throw error\n" +
                "        return callback(new Error('Signup disabled'));\n" +
                "      })\n" +
                "      .catch(function(err){\n" +
                "        callback(err);\n" +
                "      });\n" +
                "\n" +
                "    return;\n" +
                "  }\n" +
                "\n" +
                "  // if flag is enabled, throw error\n" +
                "  if (user.app_metadata.is_signup) {\n" +
                "    return callback(new Error('Signup disabled'));\n" +
                "  }\n" +
                "\n" +
                "  // else it is a non social login or it is not a signup\n" +
                "  callback(null, user, context);\n" +
                "}";
        testScriptNoAppReferences = "function emailVerified(user, context, callback) {\n" +
                "  if (!user.email_verified) {\n" +
                "    return callback(new UnauthorizedError('Please verify your email before logging in.'));\n" +
                "  } else {\n" +
                "    return callback(null, user, context);\n" +
                "  }\n" +
                "}";

        rules = new Rule[]{new Rule("1", true, testScript1AppName, "test rule for Fruit Bot", 1, "1"),
                new Rule("2", true, testScript3ClientIDs, "test rule for Fruit Bot, Byte Vegetables, and Fruitprism", 1, "1"),
                new Rule("3", true, testScriptNoAppReferences, "test rule for All Applications", 1, "1")};
    }

    @Test
    void getTenantClientRules() {
        HashMap<String, List<String>> expected = new HashMap<>();

        List<String> fruitBotExpected = new LinkedList<>();
        fruitBotExpected.add("test rule for Fruit Bot");
        fruitBotExpected.add("test rule for Fruit Bot, Byte Vegetables, and Fruitprism");

        List<String> byteVegetablesExpected = new LinkedList<>();
        byteVegetablesExpected.add("test rule for Fruit Bot, Byte Vegetables, and Fruitprism");

        List<String> fruitprismExpected = new LinkedList<>();
        fruitprismExpected.add("test rule for Fruit Bot, Byte Vegetables, and Fruitprism");

        List<String> allApplicationsExpected = new LinkedList<>();
        allApplicationsExpected.add("test rule for All Applications");

        expected.put("Fruit Bot", fruitBotExpected);
        expected.put("Byte Vegetables", byteVegetablesExpected);
        expected.put("Fruitprism", fruitprismExpected);
        expected.put("All Applications", allApplicationsExpected);

        HashMap<String, List<String>> actual = TenantClientRules.getTenantClientRules(clients, rules);

        assertEquals(expected, actual);
    }
}
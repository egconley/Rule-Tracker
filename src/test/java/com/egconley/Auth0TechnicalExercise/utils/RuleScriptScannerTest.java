package com.egconley.Auth0TechnicalExercise.utils;

import com.egconley.Auth0TechnicalExercise.models.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleScriptScannerTest {

    RuleScriptScanner scanner;
    String testScript1AppName;
    String testScript3ClientIDs;
    String testScriptNoAppReferences;
    Client[] clients;

    @BeforeEach
    void setUp() {
        scanner = new RuleScriptScanner();
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
        clients = new Client[]{new Client("mkrG83lsKIOadGrWTS6fXb3QXASUABSn", "test tenant", "Fruit Bot"),
                new Client("KyFlLCELqa8flAt47exfXo5qz8GT99Rv", "test tenant", "Byte Vegetables"),
                new Client("KLakvhTX67hRwXeAV7pBl3F8ZG4UD6tx", "test tenant", "Fruitprism")};
    }

    @Test
    public void getAppNames_test1AppName() {
        List<String> actual = scanner.getAppNames(testScript1AppName, clients);
        List<String> expected = new LinkedList<>();
        expected.add("Fruit Bot");
        assertEquals(expected, actual);
    }

    @Test
    public void getAppNames_testScript3ClientIDs() {
        List<String> actual = scanner.getAppNames(testScript3ClientIDs, clients);
        List<String> expected = new LinkedList<>();
        expected.add("Fruit Bot");
        expected.add("Byte Vegetables");
        expected.add("Fruitprism");
        assertEquals(expected, actual);
    }

    @Test
    public void getAppNames_testScriptNoAppReferences() {
        List<String> actual = scanner.getAppNames(testScriptNoAppReferences, clients);
        List<String> expected = new LinkedList<>();
        assertEquals(expected, actual);
    }
}
package com.egconley.Auth0TechnicalExercise;

import com.egconley.Auth0TechnicalExercise.models.AllClientRules;
import com.egconley.Auth0TechnicalExercise.models.Tenant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class Auth0TechnicalExerciseApplication {

	public static void main(String[] args) {
		// TODO: com.egconley.Auth0DSETechExercise.App User: add own tenant name
		String tenantName = "dev-s4sen828";
		List<String> tenantNames = new LinkedList<>();
		tenantNames.add(tenantName);
		
		HashMap<String, List<String>> result = AllClientRules.getAllClientRules(tenantNames);
		System.out.println("HOORAY!!!" + result.keySet().toString());
		SpringApplication.run(Auth0TechnicalExerciseApplication.class, args);
	}

}

package com.alessandragodoy.accountms.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Configuration class for OpenAPI documentation.
 * This class sets up the OpenAPI definition for the Customer Microservice.
 */
@OpenAPIDefinition(
		info = @Info(
				title = "Banking system - Account Microservice",
				description = "API for managing accounts with CRUD operations",
				version = "1.0.0",
				contact = @Contact(
						name = "Alessandra Godoy",
						email = "api@alessandragodoy.com"
				)
		))
public class OpenApiConfig {
}


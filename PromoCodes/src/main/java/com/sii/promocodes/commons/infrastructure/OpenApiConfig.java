package com.sii.promocodes.commons.infrastructure;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "REST API for Promo Codes", version = "1.0",
    description = "REST API application for managing promo codes, products and purchases."
))
public class OpenApiConfig {

}

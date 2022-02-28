package com.geekbrains.spring.web.cart.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.core-service")
@Data
public class ProductServiceIntegrationProperties {
    private String url;
}

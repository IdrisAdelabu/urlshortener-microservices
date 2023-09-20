package com.gbadegesin.urlgen.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "urlgen")
@Data
public class UrlGenServiceConfig {

    private String baseUrl;
}

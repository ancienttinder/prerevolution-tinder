package ru.digitalleague.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.MILLIS;

@Configuration
@EnableConfigurationProperties
public class RestTemplateConfig {
    @Value("${route.server.url}")
    private String baseUrl;

    @Value("${rest.service.connect.timeout}")
    private String connectTimeout;

    @Value("${rest.service.read.timeout}")
    private String readTimeout;

    @Bean
    @Qualifier("restServiceTemplate")
    public RestTemplate restServiceTemplate() {
        return buildRestTemplate(baseUrl);
    }

    private RestTemplate buildRestTemplate(String baseUri) {
        if (baseUri.endsWith("/")) {
            baseUri = baseUri.substring(0, baseUri.length() - 1);
        }

        return new RestTemplateBuilder()
                .rootUri(baseUri)
                .setConnectTimeout(Duration.of(Long.parseLong(connectTimeout), MILLIS))
                .setReadTimeout(Duration.of(Long.parseLong(readTimeout), MILLIS))
                .build();
    }
}

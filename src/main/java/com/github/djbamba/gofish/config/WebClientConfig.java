package com.github.djbamba.gofish.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class WebClientConfig {

  @Value("${weather.service.url}")
  private String weatherServiceUrl;

  @Bean
  public WebClient webClient() {
    DefaultUriBuilderFactory defaultUriBuilderFactory =
        new DefaultUriBuilderFactory(
            UriComponentsBuilder.fromHttpUrl(weatherServiceUrl));

    return WebClient.builder().uriBuilderFactory(defaultUriBuilderFactory).build();
  }
}

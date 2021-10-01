package com.github.djbamba.gofish.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WeatherClient {

  @Autowired
  private final WebClient client;
  @Value("${weather.service.url}")
  private String weatherServiceUrl;
  private Logger log = LoggerFactory.getLogger(WeatherClient.class);

  public WeatherClient(WebClient client) {
    this.client = client;
  }

  public Mono<ResponseEntity<String>> getCurrentWeather(String zip) {
    return this.client.get()
        .uri(uriBuilder ->
            uriBuilder.path("/current-weather").queryParam("zip", zip).build())
        .retrieve()
        .toEntity(String.class)
        .switchIfEmpty(Mono.just(ResponseEntity.unprocessableEntity().build()));
  }

}

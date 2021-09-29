package com.github.djbamba.gofish.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WeatherClient {

  @Autowired
  private final WebClient client;
  @Value("${weather.service.url}")
  private String weatherServiceUrl;

  public WeatherClient(WebClient client) {
    this.client = client;
  }

  public String getCurrentWeather(String zip) {
    Mono<String> weatherMono = this.client.get().uri(uriBuilder ->
            uriBuilder.path("/current-weather")
                .queryParam("zip", zip).build()).retrieve()
        .bodyToMono(String.class);

    return weatherMono.block();
  }

}

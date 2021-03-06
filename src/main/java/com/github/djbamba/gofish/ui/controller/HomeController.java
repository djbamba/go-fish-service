package com.github.djbamba.gofish.ui.controller;

import com.github.djbamba.gofish.client.WeatherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @Autowired
  private final WeatherClient weatherClient;

  public HomeController(WeatherClient weatherClient) {
    this.weatherClient = weatherClient;
  }

  @GetMapping("/weather")
  public ResponseEntity<String> getCurrentWeather(@RequestParam String zip) {
    return ResponseEntity.ok(weatherClient.getCurrentWeather(zip));
  }
}

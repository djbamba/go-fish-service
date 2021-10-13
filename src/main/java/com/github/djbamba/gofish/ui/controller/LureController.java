package com.github.djbamba.gofish.ui.controller;

import static java.lang.String.format;

import com.github.djbamba.gofish.ui.model.products.Lure;
import com.github.djbamba.gofish.ui.service.LureService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lures")
@RequiredArgsConstructor
public class LureController {

  private static final Logger log = LoggerFactory.getLogger(LureController.class);
  private final LureService lureService;


  @GetMapping
  public ResponseEntity<List<Lure>> allProducts() {
    return ResponseEntity.ok(lureService.getAllLures());
  }

  @PostMapping
  public ResponseEntity<Lure> addLure(@RequestBody Lure lure) {
    log.debug("Add: {}", lure);
    return ResponseEntity.status(HttpStatus.CREATED).body(lureService.addLure(lure));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Lure> findLureById(@PathVariable String id) {
    log.debug("findLureById: {}", id);

    return lureService.findLureById(id)
        .map(lure -> ResponseEntity.ok()
            .header(HttpHeaders.LOCATION, format("/lures/%s", id))
            .body(lure))
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping
  public ResponseEntity<Lure> updateLure(@RequestBody Lure lure) {
    if (lure == null || lure.getId() == null) {
      return ResponseEntity.badRequest().build();
    }
    log.debug("Update: {}", lure);

    return lureService.findLureById(lure.getId())
        .map(l -> ResponseEntity.ok()
            .header(HttpHeaders.LOCATION, format("/lures/%s", l.getId()))
            .body(lure))
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteLureById(@PathVariable String id) {
    log.debug("Delete: {}", id);

    return lureService.findLureById(id)
        .map(l -> {
          lureService.deleteLureById(l.getId());
          return ResponseEntity.ok().build();
        })
        .orElse(ResponseEntity.notFound().build());
  }
}

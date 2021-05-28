package com.github.djbamba.gofish.ui.controller;

import com.github.djbamba.gofish.ui.model.products.Lure;
import com.github.djbamba.gofish.ui.service.LureService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


  @GetMapping("/all")
  public ResponseEntity<List<Lure>> allProducts() {
    return ResponseEntity.ok(lureService.getAllLures());
  }

  @PostMapping("/add")
  public ResponseEntity<Lure> addLure(@RequestBody Lure lure) {
    return ResponseEntity.ok(lureService.addLure(lure));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Lure> findLureById(@PathVariable String id) {
    Optional<Lure> lure = lureService.findLureById(id);
    lure.ifPresent(l -> log.info("{}", l));

    return lure.map(ResponseEntity::ok)
        .orElseThrow(() -> new NoSuchElementException(String.format("ID: [%s] does not exist", id)));
  }

  @PutMapping("/update")
  public ResponseEntity<Lure> updateLure(@RequestBody Lure lure) {
    return ResponseEntity.ok(lureService.updateLure(lure));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteLureById(@PathVariable String id) {
    Optional<Lure> deleteLure = lureService.findLureById(id);

    return deleteLure
        .map(l -> {
              lureService.deleteLureById(l.getId());
              return ResponseEntity.ok().body(String.format("Deleted ID: [%s]", id));
            })
        .orElseThrow(
            () -> new NoSuchElementException(String.format("ID: [%s] does not exist", id)));
  }
}

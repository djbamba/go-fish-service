package com.github.djbamba.gofish.ui.service;

import com.github.djbamba.gofish.ui.model.products.Lure;
import com.github.djbamba.gofish.ui.repo.LureRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LureService {
  private final LureRepository lureRepo;

  public List<Lure> getAllLures() {
    return lureRepo.findAll();
  }

  public Lure addLure(Lure lure) {
    return lureRepo.insert(lure);
  }

  public Optional<Lure> findLureById(String id) {
    return lureRepo.findById(id);
  }

  public Lure updateLure(Lure lure) {
    return lureRepo.save(lure);
  }

  public void deleteLureById(String id) {
    lureRepo.deleteById(id);
  }
}

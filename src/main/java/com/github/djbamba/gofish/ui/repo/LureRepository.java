package com.github.djbamba.gofish.ui.repo;

import com.github.djbamba.gofish.ui.model.products.Lure;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LureRepository extends MongoRepository<Lure,String> {
//  Optional<Lure> findById(String id);
}

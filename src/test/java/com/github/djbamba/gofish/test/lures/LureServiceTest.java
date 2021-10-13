package com.github.djbamba.gofish.test.lures;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.djbamba.gofish.ui.model.products.Lure;
import com.github.djbamba.gofish.ui.model.products.Lure.LureType;
import com.github.djbamba.gofish.ui.repo.LureRepository;
import com.github.djbamba.gofish.ui.service.LureService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LureServiceTest {

  @Autowired
  private LureService service;
  @MockBean
  private LureRepository repo;

  @Test
  @DisplayName("Test findLureById - Success")
  public void testFindByIdSuccess() {
    Lure mockLure = Lure.builder()
        .id("XYZ")
        .name("Sexy Shad")
        .colorWay("Bama Bug")
        .imageUrl("http://dummyimage.jpg")
        .productUrl("http://dummyproducturl.html")
        .price(new BigDecimal("9.95"))
        .type(LureType.HARD_BAIT)
        .build();
    when(repo.findById("XYZ")).thenReturn(Optional.of(mockLure));

    Optional<Lure> actualLure = service.findLureById("XYZ");

    Assertions.assertTrue(actualLure.isPresent(), "Lure was not found");
    Assertions.assertSame(mockLure, actualLure.get(), "Lure should be the same");
  }

  @Test
  @DisplayName("Test findLureById - Not Found")
  public void testFindByIdNotFound() {
    when(repo.findById(any())).thenReturn(Optional.empty());

    Optional<Lure> actualLure = service.findLureById("XYZ");

    Assertions.assertFalse(actualLure.isPresent(), "Lure was found when it shouldn't be");
  }

  @Test
  @DisplayName("Test getAllLures")
  public void testGetAllLures() {
    Lure lure0 = Lure.builder()
        .id("ABC")
        .name("Turbo Crawz")
        .colorWay("Okeechobee Bug")
        .imageUrl("http://dummyimage3.jpg")
        .productUrl("http://dummyproducturl3.html")
        .price(new BigDecimal("11.00"))
        .type(LureType.HARD_BAIT)
        .build();
    Lure lure1 = Lure.builder()
        .id("XYZ")
        .name("Sexy Shad")
        .colorWay("Bama Bug")
        .imageUrl("http://dummyimage2.jpg")
        .productUrl("http://dummyproducturl2.html")
        .price(new BigDecimal("10.55"))
        .type(LureType.HARD_BAIT)
        .build();
    when(repo.findAll()).thenReturn(Arrays.asList(lure0, lure1));

    List<Lure> lures = service.getAllLures();

    Assertions.assertEquals(2, lures.size(), "getAllLures should return 2 lures");
  }

  @Test
  @DisplayName("Test addLure")
  public void testAddLure() {
    Lure mockLure = Lure.builder()
        .id("ABC")
        .name("Turbo Crawz")
        .colorWay("Okeechobee Bug")
        .imageUrl("http://dummyimage3.jpg")
        .productUrl("http://dummyproducturl3.html")
        .price(new BigDecimal("11.00"))
        .type(LureType.HARD_BAIT)
        .build();

    when(repo.insert(any(Lure.class))).thenReturn(mockLure);

    Lure savedLure = service.addLure(mockLure);

    Assertions.assertNotNull(savedLure, "Lure returned afters save should not be null");
    Assertions.assertNotNull(savedLure.getId(),
        "Lure returned afters save should have an auto-generated ID");
  }

}

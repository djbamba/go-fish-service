package com.github.djbamba.gofish;

import com.github.djbamba.gofish.ui.model.products.Lure;
import com.github.djbamba.gofish.ui.model.products.Lure.LureType;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class GoFishCli implements CommandLineRunner {
  private final Logger log = LoggerFactory.getLogger(GoFishCli.class);

  @Autowired private MongoTemplate template;

  @Override
  public void run(String... args) throws Exception {
    if (template.findAll(Lure.class).isEmpty()) {
      Lure lure =
          Lure.builder()
              .imageUrl(
                  "https://basspro.scene7.com/is/image/BassPro/2825406_100359928_is?$Prod_PLPThumb$")
              .name("Googan Baits Krackin' Craws")
              .colorWay("Okeechobee Craw")
              .productUrl("https://www.basspro.com/shop/en/googan-baits-krackin-craws")
              .price(BigDecimal.valueOf(5.99))
              .type(LureType.SOFT_BAIT)
              .build();
      template.insert(lure);
      lure =
          Lure.builder()
              .imageUrl(
                  "https://basspro.scene7.com/is/image/BassPro/2923740_100359928_is?$Prod_PLPThumb$")
              .name("Googan Baits Krackin' Craws")
              .colorWay("Green Pumpkin Purple")
              .productUrl("https://www.basspro.com/shop/en/googan-baits-krackin-craws")
              .price(BigDecimal.valueOf(5.99))
              .type(LureType.SOFT_BAIT)
              .build();
      template.insert(lure);
      lure =
          Lure.builder()
              .imageUrl(
                  "https://basspro.scene7.com/is/image/BassPro/2825403_100359928_is?$Prod_PLPThumb$")
              .name("Googan Baits Krackin' Craws")
              .colorWay("Bamba Bug")
              .productUrl("https://www.basspro.com/shop/en/googan-baits-krackin-craws")
              .price(BigDecimal.valueOf(5.99))
              .type(LureType.SOFT_BAIT)
              .build();
      template.insert(lure);
      lure =
          Lure.builder()
              .imageUrl(
                  "https://basspro.scene7.com/is/image/BassPro/2825402_100359928_is?$Prod_PLPThumb$")
              .name("Googan Baits Krackin' Craws")
              .colorWay("Albama Craw")
              .productUrl("https://www.basspro.com/shop/en/googan-baits-krackin-craws")
              .price(BigDecimal.valueOf(5.99))
              .type(LureType.SOFT_BAIT)
              .build();
      template.insert(lure);
      log.info("Seeded");
    }
  }
}

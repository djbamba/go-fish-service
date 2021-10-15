package com.github.djbamba.gofish.ui.model.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "lures")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Lure {
  @Id private String id;
  @Field("productName")
  private String name;
  @Field("color")
  private String colorWay;
  @Field("imgSrc")
  private String imageUrl;
  @Field("productSrc")
  private String productUrl;
  private BigDecimal price;
  @Field("lureType")
  private LureType type;

  public enum LureType {
    SOFT_BAIT,
    HARD_BAIT,
    SPINNER_BUZZ_BAIT
  }
}



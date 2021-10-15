package com.github.djbamba.gofish.test.it;

import static com.github.djbamba.gofish.json.JsonMapper.writeValueAsString;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.djbamba.gofish.test.ext.MongoExtension;
import com.github.djbamba.gofish.test.ext.MongoJsonFile;
import com.github.djbamba.gofish.test.ext.MongoTemplateProvider;
import com.github.djbamba.gofish.ui.model.products.Lure;
import com.github.djbamba.gofish.ui.model.products.Lure.LureType;
import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@ExtendWith(MongoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LureServiceIntegrationTest implements MongoTemplateProvider {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private MockMvc mockMvc;

  @Override
  public MongoTemplate getMongoTemplate() {
    return mongoTemplate;
  }

  @Test
  @DisplayName("GET /lures - Success")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  public void testGetLuresSuccess() throws Exception {
    mockMvc.perform(get("/lures"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isNotEmpty());
  }

  @Test
  @DisplayName("GET /lures/AEN - Found")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  void testGetLureByIdFound() throws Exception {
    mockMvc.perform(get("/lures/{id}", "AEN"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", Matchers.is("AEN")))
        .andExpect(jsonPath("$.name", Matchers.is("Krackin' Craws")))
        .andExpect(jsonPath("$.colorWay", Matchers.is("Alabama Craw")))
        .andExpect(jsonPath("$.imageUrl", Matchers.is(
            "http://dummyimage0.jpg")))
        .andExpect(jsonPath("$.productUrl",
            Matchers.is("http://dummyproducturl0.html")))
        .andExpect(jsonPath("$.price", Matchers.is(5.97)))
        .andExpect(jsonPath("$.type", Matchers.is("SOFT_BAIT")));
  }

  @Test
  @DisplayName("GET /lures/EFG - Not Found")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  public void testGetLureByIdNotFound() throws Exception {
    mockMvc.perform(get("/lures/{id}", "EFG"))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("POST /lures - Success")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  public void testCreateLureSuccess() throws Exception {
    Lure postLure = Lure.builder()
        .name("Pompadour Jr")
        .colorWay("Sexy Shad")
        .imageUrl("http://dummyimagex.jpg")
        .productUrl("http://dummyproducturlx.html")
        .price(new BigDecimal("3.50"))
        .type(LureType.HARD_BAIT)
        .build();

    mockMvc.perform(post("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsString(postLure)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        .andExpect(jsonPath("$.id", any(String.class)))
        .andExpect(jsonPath("$.name", is(postLure.getName())))
        .andExpect(jsonPath("$.colorWay", is(postLure.getColorWay())))
        .andExpect(jsonPath("$.imageUrl", is(postLure.getImageUrl())))
        .andExpect(jsonPath("$.productUrl", is(postLure.getProductUrl())))
        .andExpect(jsonPath("$.price", is(postLure.getPrice().doubleValue())))
        .andExpect(jsonPath("$.type", is(postLure.getType().name())));
  }

  @Test
  @DisplayName("POST /lures - Bad Request - Malformed JSON")
  public void testCreateLureBadRequestMalformedJson() throws Exception {
    mockMvc.perform(post("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{"
                + "        \"name\": \"Googan Baits Krackin' Craws\",\n"
                + "        \"colorWay\": \"Okeechobee Craws,\n"
                + "        \"price\": 10.56,\n"
                + "        \"type\": \"SOFT_BAIT\"\n"
                + "    }"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Malformed JSON payload")));
  }

  @Test
  @DisplayName("PUT /lures/AEN - Success")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  public void testPutLureSuccess() throws Exception {
    Lure putLure = Lure.builder()
        .id("AEN")
        .name("Turbo Crawz")
        .colorWay("Okeechobee Bug")
        .imageUrl("http://dummyimageU.jpg")
        .productUrl("http://dummyproducturlU.html")
        .price(new BigDecimal("11.00"))
        .type(LureType.HARD_BAIT)
        .build();

    mockMvc.perform(put("/lures", putLure.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsString(putLure)))
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.LOCATION, "/lures/AEN"))

        .andExpect(jsonPath("$.id", is(putLure.getId())))
        .andExpect(jsonPath("$.name", is(putLure.getName())))
        .andExpect(jsonPath("$.colorWay", is(putLure.getColorWay())))
        .andExpect(jsonPath("$.imageUrl", is(putLure.getImageUrl())))
        .andExpect(jsonPath("$.productUrl", is(putLure.getProductUrl())))
        .andExpect(jsonPath("$.price", is(putLure.getPrice().doubleValue())))
        .andExpect(jsonPath("$.type", is(putLure.getType().name())));
  }

  @Test
  @DisplayName("PUT /lures/ABC - Not Found")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  public void testPutLureNotFound() throws Exception {
    Lure putLure = Lure.builder()
        .id("ABC")
        .name("Turbo Crawz")
        .colorWay("Okeechobee Bug")
        .imageUrl("http://dummyimageU.jpg")
        .productUrl("http://dummyproducturlU.html")
        .price(new BigDecimal("11.00"))
        .type(LureType.HARD_BAIT)
        .build();

    mockMvc.perform(put("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsString(putLure)))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("PUT /lures - Bad Request - Null ID")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  public void testPutLureBadRequestNullId() throws Exception {
    Lure putLure = Lure.builder()
        .name("Turbo Crawz")
        .colorWay("Okeechobee Bug")
        .imageUrl("http://dummyimageU.jpg")
        .productUrl("http://dummyproducturlU.html")
        .price(new BigDecimal("11.00"))
        .type(LureType.HARD_BAIT)
        .build();

    mockMvc.perform(put("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsString(putLure)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("PUT /lures - Bad Request - No Content")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  public void testPutLureBadRequestNoContent() throws Exception {
    mockMvc.perform(put("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(""))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("PUT /lures - Bad Request - Malformed JSON")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  public void testPutLureBadRequestMalformedJson() throws Exception {
    mockMvc.perform(post("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{"
                + "        \"name\": \"Googan Baits Krackin' Craws\",\n"
                + "        \"colorWay\": \"Okeechobee Craws,\n"
                + "        \"price\": 10.56,\n"
                + "        \"type\": \"SOFT_BAIT\"\n"
                + "    }"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Malformed JSON payload")));
  }

  @Test
  @DisplayName("DELETE /lures/AEN - Success")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  public void testDeleteLureSuccess() throws Exception {
    mockMvc.perform(delete("/lures/{id}", "AEN"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/lures{id}", "AEN"))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("DELETE /lures/ABC - Not Found")
  @MongoJsonFile(value = "test-lures.json", classType = Lure.class, collectionName = "lures")
  public void testDeleteLureNotFound() throws Exception {
    mockMvc.perform(delete("/lures/{id}", "ABC"))
        .andExpect(status().isNotFound());
  }
}

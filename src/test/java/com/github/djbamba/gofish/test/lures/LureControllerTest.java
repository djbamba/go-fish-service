package com.github.djbamba.gofish.test.lures;

import static com.github.djbamba.gofish.json.JsonMapper.writeValueAsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.djbamba.gofish.ui.model.products.Lure;
import com.github.djbamba.gofish.ui.model.products.Lure.LureType;
import com.github.djbamba.gofish.ui.service.LureService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LureControllerTest {

  @MockBean
  private LureService service;
  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("GET /lures/ABC - Found")
  void testGetLureByIdFound() throws Exception {
    Lure mockLure = Lure.builder()
        .id("ABC")
        .name("Sexy Shad")
        .colorWay("Bama Bug")
        .imageUrl("http://dummyimage.jpg")
        .productUrl("http://dummyproducturl.html")
        .price(new BigDecimal("9.95"))
        .type(LureType.HARD_BAIT)
        .build();

    when(service.findLureById("ABC")).thenReturn(Optional.of(mockLure));

    mockMvc.perform(get("/lures/{id}", "ABC"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(header().string("location", "/lures/ABC"))
        .andExpect(jsonPath("$.id", is("ABC")))
        .andExpect(jsonPath("$.name", is("Sexy Shad")))
        .andExpect(jsonPath("$.colorWay", is("Bama Bug")))
        .andExpect(jsonPath("$.imageUrl", is("http://dummyimage.jpg")))
        .andExpect(jsonPath("$.productUrl", is("http://dummyproducturl.html")))
        .andExpect(jsonPath("$.price", is(9.95)));
  }

  @Test
  @DisplayName("GET /lures/ABC - Not Found")
  public void testGetLureByIdNotFound() throws Exception {
    when(service.findLureById("ABC")).thenReturn(Optional.empty());

    mockMvc.perform(get("/lures/{id}", "ABC"))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("POST /lures - Success")
  public void testCreateLure() throws Exception {
    Lure postLure = Lure.builder()
        .name("Sexy Shad")
        .colorWay("Bama Bug")
        .imageUrl("http://dummyimage2.jpg")
        .productUrl("http://dummyproducturl2.html")
        .price(new BigDecimal("10.55"))
        .type(LureType.HARD_BAIT)
        .build();
    Lure mockLure = Lure.builder()
        .id("AEN")
        .name("Sexy Shad")
        .colorWay("Bama Bug")
        .imageUrl("http://dummyimage2.jpg")
        .productUrl("http://dummyproducturl2.html")
        .price(new BigDecimal("10.55"))
        .type(LureType.HARD_BAIT)
        .build();

    when(service.addLure(any())).thenReturn(mockLure);

    mockMvc.perform(post("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsString(postLure)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is("AEN")))
        .andExpect(jsonPath("$.name", is("Sexy Shad")))
        .andExpect(jsonPath("$.colorWay", is("Bama Bug")))
        .andExpect(jsonPath("$.imageUrl", is("http://dummyimage2.jpg")))
        .andExpect(jsonPath("$.productUrl", is("http://dummyproducturl2.html")))
        .andExpect(jsonPath("$.price", is(10.55)));
  }

  @Test
  @DisplayName("POST /lures - Bad Request - Malformed JSON")
  public void testCreateLureBadRequestMalformedJson() throws Exception {
    when(service.findLureById(any())).thenReturn(Optional.empty());

    mockMvc.perform(put("/lures")
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
  public void testLurePutSuccess() throws Exception {
    Lure putLure = Lure.builder()
        .id("AEN")
        .name("Turbo Crawz")
        .colorWay("Okeechobee Bug")
        .imageUrl("http://dummyimage3.jpg")
        .productUrl("http://dummyproducturl3.html")
        .price(new BigDecimal("11.00"))
        .type(LureType.HARD_BAIT)
        .build();
    Lure mockLure = Lure.builder()
        .id("AEN")
        .name("Sexy Shad")
        .colorWay("Bama Bug")
        .imageUrl("http://dummyimage2.jpg")
        .productUrl("http://dummyproducturl2.html")
        .price(new BigDecimal("10.55"))
        .type(LureType.HARD_BAIT)
        .build();

    when(service.findLureById("AEN")).thenReturn(Optional.of(mockLure));
    when(service.updateLure(any())).thenReturn(putLure);

    mockMvc.perform(put("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsString(putLure)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(header().string(HttpHeaders.LOCATION, "/lures/AEN"))

        .andExpect(jsonPath("$.id", is("AEN")))
        .andExpect(jsonPath("$.name", is("Turbo Crawz")))
        .andExpect(jsonPath("$.colorWay", is("Okeechobee Bug")))
        .andExpect(jsonPath("$.imageUrl", is("http://dummyimage3.jpg")))
        .andExpect(jsonPath("$.productUrl", is("http://dummyproducturl3.html")))
        .andExpect(jsonPath("$.price", is(11.00)));
  }

  @Test
  @DisplayName("PUT /lures/AEN - Not Found")
  public void testLurePutNotFound() throws Exception {
    Lure putLure = Lure.builder()
        .id("AEN")
        .name("Sexy Shad")
        .colorWay("Bama Bug")
        .imageUrl("http://dummyimage.jpg")
        .productUrl("http://dummyproducturl.html")
        .price(new BigDecimal("10.55"))
        .type(LureType.HARD_BAIT)
        .build();

    when(service.findLureById(any())).thenReturn(Optional.empty());

    mockMvc.perform(put("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsString(putLure)))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("PUT /lures/AEN - Bad Request - Null ID")
  public void testLurePutBadRequestNullId() throws Exception {
    Lure putLure = Lure.builder()
        .name("Sexy Shad")
        .colorWay("Bama Bug")
        .imageUrl("http://dummyimage.jpg")
        .productUrl("http://dummyproducturl.html")
        .price(new BigDecimal("10.55"))
        .type(LureType.HARD_BAIT)
        .build();

    when(service.findLureById(any())).thenReturn(Optional.empty());

    mockMvc.perform(put("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsString(putLure)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("PUT /lures/AEN - Bad Request - No Content")
  public void testLurePutBadRequestNoContent() throws Exception {
    when(service.findLureById(any())).thenReturn(Optional.empty());

    mockMvc.perform(put("/lures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(""))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("PUT /lures/AEN - Bad Request - Malformed JSON")
  public void testLurePutBadRequestMalformedJson() throws Exception {
    when(service.findLureById(any())).thenReturn(Optional.empty());

    mockMvc.perform(put("/lures")
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
  public void testLureDeleteSuccess() throws Exception {
    Lure deleteLure = Lure.builder()
        .id("AEN")
        .name("Sexy Shad")
        .colorWay("Bama Bug")
        .imageUrl("http://dummyimage.jpg")
        .productUrl("http://dummyproducturl.html")
        .price(new BigDecimal("10.55"))
        .type(LureType.HARD_BAIT)
        .build();

    when(service.findLureById("AEN")).thenReturn(Optional.of(deleteLure));
    doNothing().when(service).deleteLureById("AEN");

    mockMvc.perform(delete("/lures/{id}", "AEN"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("DELETE /lures/AEN - Not Found")
  public void testLureDeleteNotFound() throws Exception {

    when(service.findLureById("AEN")).thenReturn(Optional.empty());
    doNothing().when(service).deleteLureById("AEN");

    mockMvc.perform(delete("/lures/{id}", "AEN"))
        .andExpect(status().isNotFound());
  }

}

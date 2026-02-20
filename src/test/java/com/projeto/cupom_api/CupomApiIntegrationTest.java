package com.projeto.cupom_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CupomApiIntegrationTest {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  @Test
  void shouldCreateGetAndSoftDeletecupom() throws Exception {
    String payload = objectMapper.writeValueAsString(Map.of(
        "code", "ab-12!c3",
        "descricao", "Cupom teste",
        "valorDesconto", 10.5,
        "dataExpiracao", LocalDate.now().plusDays(3).toString(),
        "publicado", true
    ));

    String responseBody = mockMvc.perform(post("/cupom")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.code").value("AB12C3"))
        .andExpect(jsonPath("$.status").value("ACTIVE"))
        .andExpect(jsonPath("$.publicado").value(true))
        .andReturn()
        .getResponse()
        .getContentAsString();

    Long id = objectMapper.readTree(responseBody).get("id").asLong();

    mockMvc.perform(get("/cupom/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.code").value("AB12C3"));

    mockMvc.perform(delete("/cupom/{id}", id))
        .andExpect(status().isNoContent());

    mockMvc.perform(get("/cupom/{id}", id))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldRejectCreateWhenInvalidCodeLengthAfterNormalize() throws Exception {
    String payload = objectMapper.writeValueAsString(Map.of(
        "code", "A-1",
        "descricao", "Cupom",
        "valorDesconto", 1.0,
        "dataExpiracao", LocalDate.now().plusDays(1).toString()
    ));

    mockMvc.perform(post("/cupom")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", containsString("Código deve ter 6 dígitos alfanuméricos")));
  }

  @Test
  void shouldRejectDeleteWhenAlreadyDeleted() throws Exception {
    String payload = objectMapper.writeValueAsString(Map.of(
        "code", "ZZ-99!!zz",
        "descricao", "Cupom",
        "valorDesconto", "1.0",
        "dataExpiracao", LocalDate.now().plusDays(3).toString()
    ));

    String responseBody = mockMvc.perform(post("/cupom")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    Long id = objectMapper.readTree(responseBody).get("id").asLong();

    mockMvc.perform(delete("/cupom/{id}", id))
        .andExpect(status().isNoContent());

    mockMvc.perform(delete("/cupom/{id}", id))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.message").value("Cupom já deletado."));
  }
}

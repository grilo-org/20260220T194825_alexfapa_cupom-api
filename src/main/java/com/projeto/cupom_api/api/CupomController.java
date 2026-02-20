package com.projeto.cupom_api.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.cupom_api.api.dto.CreateCupomRequest;
import com.projeto.cupom_api.api.dto.CupomResponse;
import com.projeto.cupom_api.application.CupomService;
import com.projeto.cupom_api.domain.Cupom;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cupom")
@RequiredArgsConstructor
public class CupomController {

  private final CupomService cupomService;

  @PostMapping
  public ResponseEntity<CupomResponse> create(@Valid @RequestBody CreateCupomRequest req) {
    Cupom cupom = Cupom.create(
        req.code(),
        req.descricao(),
        req.valorDesconto(),
        req.dataExpiracao(),
        req.publicado() != null && req.publicado()
    );

    Cupom created = cupomService.create(cupom);
    CupomResponse response = toResponse(created);

    return ResponseEntity.created(URI.create("/Cupom/" + response.id())).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CupomResponse> get(@PathVariable Long id) {
    Cupom cupom = cupomService.getActive(id);
    return ResponseEntity.ok(toResponse(cupom));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    cupomService.delete(id);
    return ResponseEntity.noContent().build();
  }

  private CupomResponse toResponse(Cupom c) {
    return new CupomResponse(
        c.getId(),
        c.getCode(),
        c.getDescricao(),
        c.getValorDesconto(),
        c.getDataExpiracao(),
        c.getStatus().name(),
        c.isPublicado(),
        c.isResgatado()
    );
  }
}
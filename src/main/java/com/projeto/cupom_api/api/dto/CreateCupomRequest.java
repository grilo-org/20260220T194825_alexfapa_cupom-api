package com.projeto.cupom_api.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCupomRequest(
	    String code,
	    String descricao,
	    BigDecimal valorDesconto,
	    LocalDate dataExpiracao,
	    Boolean publicado
	) {}
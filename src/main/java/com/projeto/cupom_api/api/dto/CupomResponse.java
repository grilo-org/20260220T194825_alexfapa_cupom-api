package com.projeto.cupom_api.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CupomResponse(
	    Long id,
	    String code,
	    String descricao,
	    BigDecimal valorDesconto,
	    LocalDate dataExpiracao,
	    String status,
	    boolean publicado,
	    boolean resgatado
	) {}

package com.projeto.cupom_api.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import com.projeto.cupom_api.domain.exceptions.CupomDeletadoException;
import com.projeto.cupom_api.domain.exceptions.CupomInvalidoException;

public class Cupom {
	 private Long id;
	  private String code;
	  private String descricao;
	  private BigDecimal valorDesconto;
	  private LocalDate dataExpiracao;
	  private CupomStatus status;
	  private boolean publicado;
	  private boolean resgatado;
	  private Instant dtDelete;

	  private Cupom() {}

	  public static Cupom create(String codeRaw,
	                              String descricao,
	                              BigDecimal valorDesconto,
	                              LocalDate dataExpiracao,
	                              boolean publicado) {

	    if (descricao == null || descricao.isBlank()) {
	      throw new CupomInvalidoException("Descrição é obrigatória");
	    }
	    if (valorDesconto == null) {
	      throw new CupomInvalidoException("Valor do desconto é obrigatório");
	    }
	    if (valorDesconto.compareTo(new BigDecimal("0.5")) < 0) {
	      throw new CupomInvalidoException("Valor do desconto deve ser maior que 0.5");
	    }
	    if (dataExpiracao == null) {
	      throw new CupomInvalidoException("Data de expirar é obrigatória");
	    }
	    if (dataExpiracao.isBefore(LocalDate.now())) {
	      throw new CupomInvalidoException("Data de expirar não pode ser no passado.");
	    }

	    Cupom Cupom = new Cupom();
	    Cupom.code = CupomCode.of(codeRaw).value();
	    Cupom.descricao = descricao.trim();
	    Cupom.valorDesconto = valorDesconto;
	    Cupom.dataExpiracao = dataExpiracao;
	    Cupom.publicado = publicado;
	    Cupom.resgatado = false;
	    Cupom.status = CupomStatus.ACTIVE;
	    Cupom.dtDelete = null;
	    return Cupom;
	  }

	  public void delete() {
	    if (dtDelete != null || status == CupomStatus.DELETED) {
	      throw new CupomDeletadoException();
	    }
	    this.dtDelete = Instant.now();
	    this.status = CupomStatus.DELETED;
	  }

	  public boolean isDeleted() {
	    return dtDelete != null || status == CupomStatus.DELETED;
	  }

	  public Long getId() { return id; }
	  public String getCode() { return code; }
	  public String getDescricao() { return descricao; }
	  public BigDecimal getValorDesconto() { return valorDesconto; }
	  public LocalDate getDataExpiracao() { return dataExpiracao; }
	  public CupomStatus getStatus() { return status; }
	  public boolean isPublicado() { return publicado; }
	  public boolean isResgatado() { return resgatado; }
	  public Instant getDtDelete() { return dtDelete; }

	  public void setId(Long id) { this.id = id; }
	  public void setResgatado(boolean resgatado) { this.resgatado = resgatado; }

	  @Override
	  public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Cupom Cupom)) return false;
	    return Objects.equals(id, Cupom.id);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(id);
	  }
}

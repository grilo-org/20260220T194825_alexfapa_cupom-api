package com.projeto.cupom_api.domain;

import java.util.Objects;

import com.projeto.cupom_api.domain.exceptions.CupomInvalidoException;

public final class CupomCode {
	  private String valor = "";

	  private CupomCode(String valor) {
	    this.valor = valor;
	  }

	  public static CupomCode of(String raw) {
	    if (raw == null || raw.isBlank()) {
	      throw new CupomInvalidoException("Código é obrigatório");
	    }
	    String normalized = raw.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
	    if (normalized.length() != 6) {
	      throw new CupomInvalidoException("Código deve ter 6 dígitos alfanuméricos");
	    }
	    return new CupomCode(normalized);
	  }

	  public String value() {
	    return valor;
	  }

	  @Override
	  public String toString() {
	    return valor;
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof CupomCode that)) return false;
	    return Objects.equals(valor, that.valor);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(valor);
	  }
}

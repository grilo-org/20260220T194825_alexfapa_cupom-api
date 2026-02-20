package com.projeto.cupom_api.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.projeto.cupom_api.domain.exceptions.CupomInvalidoException;

class CupomCodeTest {

	  @Test
	  void shouldNormalizeAndValidateLength6() {
	    CupomCode code = CupomCode.of("ab-12!c3");
	    assertEquals("AB12C3", code.value());
	  }

	  @Test
	  void shouldRejectWhenNot6AfterNormalize() {
	    assertThrows(CupomInvalidoException.class, () -> CupomCode.of("A-1"));
	  }

	  @Test
	  void shouldRejectBlank() {
	    assertThrows(CupomInvalidoException.class, () -> CupomCode.of("  "));
	  }
}
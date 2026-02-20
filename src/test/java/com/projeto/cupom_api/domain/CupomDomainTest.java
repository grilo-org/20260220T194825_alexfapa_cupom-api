package com.projeto.cupom_api.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.projeto.cupom_api.domain.exceptions.CupomDeletadoException;
import com.projeto.cupom_api.domain.exceptions.CupomInvalidoException;

class CouponDomainTest {

	  @Test
	  void shouldRejectDiscountLessThanMin() {
	    assertThrows(CupomInvalidoException.class, () ->
	        Cupom.create("ABC-123", "desc", new BigDecimal("0.49"), LocalDate.now().plusDays(1), false)
	    );
	  }

	  @Test
	  void shouldRejectExpirationInPast() {
	    assertThrows(CupomInvalidoException.class, () ->
	        Cupom.create("ABC-123", "desc", new BigDecimal("0.50"), LocalDate.now().minusDays(1), false)
	    );
	  }

	  @Test
	  void shouldSoftDeleteAndPreventDoubleDelete() {
	    Cupom c = Cupom.create("ABC-123", "desc", new BigDecimal("1.00"), LocalDate.now().plusDays(1), false);
	    c.delete();
	    assertTrue(c.isDeleted());
	    assertThrows(CupomDeletadoException.class, c::delete);
	  }
	}
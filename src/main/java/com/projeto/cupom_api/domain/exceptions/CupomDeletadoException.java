package com.projeto.cupom_api.domain.exceptions;

public class CupomDeletadoException extends DomainException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CupomDeletadoException() {
		super("Cupom já deletado.");
	}
}

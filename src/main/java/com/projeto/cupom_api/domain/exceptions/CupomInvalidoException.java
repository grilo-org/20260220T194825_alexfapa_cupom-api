package com.projeto.cupom_api.domain.exceptions;

public class CupomInvalidoException extends DomainException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CupomInvalidoException(String mensagem) {
		super(mensagem);		
	}

}

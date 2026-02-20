package com.projeto.cupom_api.domain.exceptions;

public class DomainException extends RuntimeException{
	public  DomainException(String mensagem) {
		super(mensagem);
	}
}

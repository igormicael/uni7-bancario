package com.br.im.bancario.utils;

public class RegraNegocioException extends Exception {

	private static final long serialVersionUID = 6974343615003070278L;
	
	public RegraNegocioException(String mensagem)
	{
		super(mensagem);
	}
	
	@Override
	public synchronized Throwable fillInStackTrace() {
		return null;
	}

}
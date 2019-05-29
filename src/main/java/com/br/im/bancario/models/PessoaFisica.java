package com.br.im.bancario.models;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;

@Audited
@Entity
@DiscriminatorValue("F")
public class PessoaFisica extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2389390586134534323L;

	private String cpf;

	public PessoaFisica() {
		super();
	}

	public PessoaFisica(Long id, String nome, Date dataNascimento, String nomeMae, Boolean ativo, String cpf) {
		super(id, nome, dataNascimento, nomeMae, ativo);
		this.cpf = cpf;
	}



	public PessoaFisica(String cpf) {
		super();
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	} 
	
}

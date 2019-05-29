package com.br.im.bancario.models;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;

@Audited
@Entity
@DiscriminatorValue("J")
public class PessoaJuridica extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 79825978379315059L;
	
	private String cnpj;
	
	public PessoaJuridica() {
		super();
	}
	
	public PessoaJuridica(Long id, String nome, Date dataNascimento, String nomeMae, Boolean ativo, String cnpj) {
		super(id, nome, dataNascimento, nomeMae, ativo);
		this.cnpj = cnpj;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	
	
}

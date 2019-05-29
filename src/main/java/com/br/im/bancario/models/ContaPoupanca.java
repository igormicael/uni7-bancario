package com.br.im.bancario.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CP")
public class ContaPoupanca extends Conta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2901361971705281614L;

}

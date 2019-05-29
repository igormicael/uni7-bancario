package com.br.im.bancario.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CC")
public class ContaCorrente extends Conta {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8317951724250298163L;

}

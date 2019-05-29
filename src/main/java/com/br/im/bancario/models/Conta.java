package com.br.im.bancario.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.br.im.bancario.utils.RegraNegocioException;

@Audited
@Entity
@Table(name = "conta")
@Inheritance
@DiscriminatorColumn(name = "Tipo")
public class Conta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8447936999126394013L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@NotNull
	private Long numero;

	@Column
	private Double saldo;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_id", nullable = false)
	private Pessoa pessoa;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "agencia_id", nullable = false)
	private Agencia agencia;
	
	@Version
	private Integer versao;

	public Conta() {
		super();
	}

	public Conta(Long id, @NotNull Long numero, Double saldo, @NotNull Pessoa pessoa, @NotNull Agencia agencia) {
		super();
		this.id = id;
		this.numero = numero;
		this.saldo = saldo;
		this.pessoa = pessoa;
		this.agencia = agencia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public void adicionarValor(Double valor) {
		if(this.saldo == null)
			this.saldo = valor;
		else
			this.saldo += valor;
	}
	
	public void removerValor(Double valor) throws RegraNegocioException {
		if(this.saldo == null) {
			throw new RegraNegocioException("Não há saldo suficiente para essa operação.");
		}else {
			if( this.saldo - valor > 0 ) {
				this.saldo -= valor;
			}else {
				throw new RegraNegocioException("Não há saldo suficiente para essa operação.");
			}
		}
	}

	
}

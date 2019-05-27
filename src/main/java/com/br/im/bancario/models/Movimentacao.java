package com.br.im.bancario.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="movimentacao")
public class Movimentacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7880366929502940231L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "conta_id", nullable = false, foreignKey=@ForeignKey(name="mov_conta_id"))
	private Conta conta;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "conta_destino_id", nullable = false, foreignKey=@ForeignKey(name="mov_conta_destino_id"))
	private Conta contaDestino;
	
	@Column
	private Date data;
	
	@Enumerated(EnumType.STRING)
	private EnumTipoMovimentacao tipoMovimentacao;

	public Movimentacao() {
		super();

	}

	public Movimentacao(Long id, @NotNull Conta conta, Date data, EnumTipoMovimentacao tipoMovimentacao) {
		super();
		this.id = id;
		this.conta = conta;
		this.data = data;
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public EnumTipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(EnumTipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}
	
}

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

import org.hibernate.envers.Audited;

@Audited
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
	
	@ManyToOne
	@JoinColumn(name = "conta_destino_id", foreignKey=@ForeignKey(name="mov_conta_destino_id"))
	private Conta contaDestino;
	
	@Column
	private Date data;
	
	@Enumerated(EnumType.STRING)
	private EnumTipoMovimentacao tipoMovimentacao;
	
	@Column
	private Double valor;
	
	public Movimentacao() {
		super();

	}
	
	public Movimentacao( @NotNull Conta conta, @NotNull Conta contaDestino, Date data,
			EnumTipoMovimentacao tipoMovimentacao, Double valor) {
		super();
		this.conta = conta;
		this.contaDestino = contaDestino;
		this.data = data;
		this.tipoMovimentacao = tipoMovimentacao;
		this.valor = valor;
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

	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}

package com.br.im.bancario.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="agencia")
public class Agencia implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5500268435040736627L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private Long numero;
	
	@OneToMany(mappedBy = "agencia", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Conta> contas;

	public Agencia() {
		super();
	}

	public Agencia(Long id, String nome, Long numero, List<Conta> contas) {
		super();
		this.id = id;
		this.nome = nome;
		this.numero = numero;
		this.contas = contas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}
}

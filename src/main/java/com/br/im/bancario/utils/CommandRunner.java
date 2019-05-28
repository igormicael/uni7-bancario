package com.br.im.bancario.utils;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.br.im.bancario.models.Agencia;
import com.br.im.bancario.models.Conta;
import com.br.im.bancario.models.EnumTipoMovimentacao;
import com.br.im.bancario.models.Movimentacao;
import com.br.im.bancario.models.Pessoa;
import com.br.im.bancario.models.PessoaJuridica;
import com.br.im.bancario.repositories.AgenciaRepository;
import com.br.im.bancario.repositories.ContaRepository;
import com.br.im.bancario.repositories.MovimentacaoRepository;
import com.br.im.bancario.repositories.PessoaRepository;

@Configuration
public class CommandRunner implements CommandLineRunner {
	
	@Autowired
	private AgenciaRepository agenciaRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private MovimentacaoRepository movimentacaoRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Agencia nubank = criarAgencia();
		Pessoa igor = criarPessoaJuridica();
		
		Conta contaIgorNubank = criarContaCorrente(nubank, igor);
		
		criarMovimentacaoDeposito(contaIgorNubank, 10D);
		criarMovimentacaoDeposito(contaIgorNubank, 10D);
		criarMovimentacaoSaque(contaIgorNubank, 1010D);
		
	}

	private Agencia criarAgencia() {
		Agencia a = new Agencia();
		a.setNome("Nubank");
		a.setNumero(123L);
		
		return agenciaRepository.save(a);
	}
	
	private Pessoa criarPessoaJuridica() {
		
		Pessoa pessoa = new PessoaJuridica(null, "Igor", new Date(), "Maria", Boolean.TRUE, "12354678");
		
		return pessoaRepository.save(pessoa);
	}
	
	private Conta criarContaCorrente(Agencia agencia, Pessoa pessoa) {
		
		Conta conta = new Conta();
		conta.setAgencia(agencia);
		conta.setPessoa(pessoa);
		conta.setNumero(666L);
		conta.setSaldo(1000D);
		
		return contaRepository.save(conta);
		
	}
	
	@Transactional
	private Movimentacao criarMovimentacaoDeposito(Conta contaOrigem, Double valor) {
		
		contaOrigem = contaRepository.findById(contaOrigem.getId()).orElse(null);
		
		if(contaOrigem != null) {
			Movimentacao movimentacao = new Movimentacao(contaOrigem, null, new Date(), EnumTipoMovimentacao.DEPOSITO , valor);
			contaOrigem.adicionarValor(valor);
			
			contaRepository.save(contaOrigem);
			return movimentacaoRepository.save(movimentacao);
		}
		
		return null;
		
	}
	
	private Movimentacao criarMovimentacaoSaque(Conta contaOrigem, Double valor) {
		
		contaOrigem = contaRepository.findById(contaOrigem.getId()).orElse(null);
		
		if(contaOrigem != null) {
			Movimentacao movimentacao = new Movimentacao(contaOrigem, null, new Date(), EnumTipoMovimentacao.SAQUE , valor);
			if(contaOrigem.getSaldo() - valor > 0) {
				contaOrigem.removerValor(valor);
				contaRepository.save(contaOrigem);
				
			}
			
			
			return movimentacaoRepository.save(movimentacao);
		}
		
		return null;
		
	}
	

}

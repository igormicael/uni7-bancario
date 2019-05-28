package com.br.im.bancario.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.br.im.bancario.models.Agencia;
import com.br.im.bancario.models.Conta;
import com.br.im.bancario.models.EnumTipoMovimentacao;
import com.br.im.bancario.models.Movimentacao;
import com.br.im.bancario.models.Pessoa;
import com.br.im.bancario.models.PessoaFisica;
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

		Conta contaIgor = criarContaCorrente(criarOuRecuperarAgencia(), criarPessoaJuridica());
		
		Conta contaCleo = criarContaCorrente(criarOuRecuperarAgencia(), criarPessoaFisica());

		realizarDeposito(contaIgor, 1000D);

		realizarSaque(contaIgor, 100D);
		
		realizarDeposito(contaCleo, 10D);
		
		realizarTranferencia(contaIgor, contaCleo, 540D);

	}

	private void realizarSaque(Conta contaIgorNubank, double d) {
		try {
			contaIgorNubank = recuperarConta(contaIgorNubank);
			criarMovimentacaoSaque(contaIgorNubank, d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void realizarDeposito(Conta contaIgorNubank, double d) {
		try {
			contaIgorNubank = recuperarConta(contaIgorNubank);
			criarMovimentacaoDeposito(contaIgorNubank, d);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Conta recuperarConta(Conta conta) {
		return contaRepository.findById(conta.getId()).orElse(null);
	}

	private Agencia criarOuRecuperarAgencia() {
		Agencia a = new Agencia();
		a.setNome("Nubank");
		a.setNumero(123L);

		Agencia agenciaSalva = agenciaRepository.findByNome(a.getNome()).orElse(null);
		
		if(agenciaSalva == null) {
			return agenciaRepository.save(a);
		}
		
		return agenciaSalva;
	}

	private Pessoa criarPessoaJuridica() {

		Pessoa pessoa = new PessoaJuridica(null, "Igor", new Date(), "Maria", Boolean.TRUE, "81.384.673/0001-94");

		return pessoaRepository.save(pessoa);
	}
	
	private Pessoa criarPessoaFisica() {
		
		Pessoa pessoa = new PessoaFisica(null, "Cleo", new Date(), "Lucia", Boolean.TRUE, "913.070.303-43");
		
		return pessoaRepository.save(pessoa);
	}

	private Conta criarContaCorrente(Agencia agencia, Pessoa pessoa) {

		Conta conta = new Conta();
		conta.setAgencia(agencia);
		conta.setPessoa(pessoa);
		conta.setNumero(666L);

		return contaRepository.save(conta);

	}

	@Transactional(rollbackFor = RegraNegocioException.class)
	private Movimentacao criarMovimentacaoDeposito(Conta contaOrigem, Double valor) {

		if (contaOrigem != null) {
			Movimentacao movimentacao = new Movimentacao(contaOrigem, null, new Date(), EnumTipoMovimentacao.DEPOSITO, valor);
			contaOrigem.adicionarValor(valor);

			contaRepository.save(contaOrigem);
			return movimentacaoRepository.save(movimentacao);
		}

		return null;

	}

	@Transactional(rollbackFor = RegraNegocioException.class)
	private Movimentacao criarMovimentacaoSaque(Conta contaOrigem, Double valor) throws RegraNegocioException {

		contaOrigem = contaRepository.findById(contaOrigem.getId()).orElse(null);

		if (contaOrigem != null) {
			Movimentacao movimentacao = new Movimentacao(contaOrigem, null, new Date(), EnumTipoMovimentacao.SAQUE,
					valor);
			if (contaOrigem.getSaldo() - valor > 0) {
				contaOrigem.removerValor(valor);
				contaRepository.save(contaOrigem);
			} else {
				throw new RegraNegocioException("Não há saldo suficiente para essa operação.");
			}
			return movimentacaoRepository.save(movimentacao);
		}

		return null;

	}
	
	@Transactional(rollbackFor = RegraNegocioException.class)
	private void realizarTranferencia(Conta contaIgor, Conta contaCleo, double d) {
		
	}

}

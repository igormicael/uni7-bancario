package com.br.im.bancario.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.br.im.bancario.models.Agencia;
import com.br.im.bancario.models.Conta;
import com.br.im.bancario.models.ContaCorrente;
import com.br.im.bancario.models.ContaPoupanca;
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

		Conta contaIgor = criarContaCorrente(criarOuRecuperarAgencia(), criarPessoaJuridica(), 666L);

		Conta contaCleo = criarContaCorrente(criarOuRecuperarAgencia(), criarPessoaFisica(), 999L);

		realizarDeposito(contaIgor, 1000D);

		realizarSaque(contaIgor, 100D);

		realizarDeposito(contaCleo, 10D);

		realizarTranferencia(contaIgor, contaCleo, 540D);
		
		System.out.println("-----------");
		System.out.println("----- Movimentações por periodo ------");
		System.out.println("-----------");
		
		LocalDate ontem = LocalDate.now().minusDays(1);
		LocalDate amanha = LocalDate.now().plusDays(1);
		
		Date dateOntem = Date.from(ontem.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date dateAmanha = Date.from(amanha.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		System.out.println(movimentacaoRepository.findByDataBetween(dateOntem, dateAmanha ));
		
		System.out.println("-----------");
		System.out.println("----- Movimentações por agencia ------");
		System.out.println("-----------");
		
		System.out.println(movimentacaoRepository.findByContaAgenciaId(contaIgor.getAgencia().getId()));
		
		System.out.println("-----------");
		System.out.println("----- Movimentações por cliente Igor ------");
		System.out.println("-----------");
		
		System.out.println(movimentacaoRepository.findByContaPessoaId(contaIgor.getPessoa().getId()));
		
		System.out.println("-----------");
		System.out.println("----- Movimentações por cliente Cleo ------");
		System.out.println("-----------");
		
		System.out.println(movimentacaoRepository.findByContaPessoaId(contaCleo.getPessoa().getId()));
		
		System.out.println("-----------");

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
		} catch (Exception e) {
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

		if (agenciaSalva == null) {
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

	private Conta criarContaCorrente(Agencia agencia, Pessoa pessoa, Long numeroConta) {

		Conta conta = new ContaCorrente();
		conta.setAgencia(agencia);
		conta.setPessoa(pessoa);
		conta.setNumero(numeroConta);

		return contaRepository.save(conta);

	}

	@Transactional(rollbackFor = RegraNegocioException.class)
	private Movimentacao criarMovimentacaoDeposito(Conta contaOrigem, Double valor) throws RegraNegocioException {
		Movimentacao movimentacao = null;
		
		if (contaOrigem != null) {
			movimentacao = new Movimentacao(contaOrigem, null, new Date(), EnumTipoMovimentacao.DEPOSITO, valor);
			movimentacao = movimentacaoRepository.save(movimentacao);
			contaOrigem.adicionarValor(valor);

			contaRepository.save(contaOrigem);
			return movimentacao; 
		}

		throw new RegraNegocioException("Conta para deposito presica ser informada.");

	}

	@Transactional(rollbackFor = RegraNegocioException.class)
	private Movimentacao criarMovimentacaoSaque(Conta contaOrigem, Double valor) throws RegraNegocioException {
		Movimentacao movimentacao = null;
		
		if (contaOrigem != null) {
			movimentacao = new Movimentacao(contaOrigem, null, new Date(), EnumTipoMovimentacao.SAQUE, valor);
			movimentacao = movimentacaoRepository.save(movimentacao);

			contaOrigem.removerValor(valor);
			contaRepository.save(contaOrigem);
			
			return movimentacao;
		}
		throw new RegraNegocioException("Conta para saque presica ser informada.");

	}

	@Transactional(rollbackFor = RegraNegocioException.class)
	private void realizarTranferencia(Conta contaOrigem, Conta contaDestino, double valorTransferencia)
			throws RegraNegocioException {

		if (contaOrigem == null) {
			throw new RegraNegocioException("Conta de origem para transferencia presica ser informada.");
		}
		
		if (contaDestino == null) {
			throw new RegraNegocioException("Conta de destino para transferencia presica ser informada.");
		}
		
		if(contaOrigem instanceof ContaPoupanca || contaDestino instanceof ContaPoupanca) {
			throw new RegraNegocioException("As duas contas precisam ser corrente para ser realizado a transferencia");
		}
		
		contaOrigem = recuperarConta(contaOrigem);
		contaDestino = recuperarConta(contaDestino);
		
		Movimentacao movimentacao = new Movimentacao(contaOrigem, contaDestino, new Date(), EnumTipoMovimentacao.TRANSFERENCIA, valorTransferencia);
		movimentacao = movimentacaoRepository.save(movimentacao);
		
		contaOrigem.removerValor(valorTransferencia);
		contaDestino.adicionarValor(valorTransferencia);
		
		contaRepository.save(contaOrigem);
		contaRepository.save(contaDestino);

	}

}

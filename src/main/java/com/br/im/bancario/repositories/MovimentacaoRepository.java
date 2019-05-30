package com.br.im.bancario.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.im.bancario.models.Movimentacao;

@Repository
public interface MovimentacaoRepository extends CrudRepository<Movimentacao, Long> {

	@Query("select m from Movimentacao m where m.data between :data1 and :data2")
	List<Movimentacao> findByDataBetween(@Param("data1")Date data1,@Param("data2") Date data2);

	List<Movimentacao> findByContaAgenciaId(Long agenciaId);

	List<Movimentacao> findByContaPessoaId(Long pessoaId);

}

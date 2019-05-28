package com.br.im.bancario.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.im.bancario.models.Agencia;

@Repository
public interface AgenciaRepository extends CrudRepository<Agencia, Long> {
	
	public Optional<Agencia> findByNome(String nome);

}

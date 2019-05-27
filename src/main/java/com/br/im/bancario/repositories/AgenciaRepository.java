package com.br.im.bancario.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.im.bancario.models.Agencia;

@Repository
public interface AgenciaRepository extends CrudRepository<Agencia, Long> {

}

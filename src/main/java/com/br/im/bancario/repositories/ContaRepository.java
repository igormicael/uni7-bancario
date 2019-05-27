package com.br.im.bancario.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.im.bancario.models.Conta;

@Repository
public interface ContaRepository extends CrudRepository<Conta, Long> {

}

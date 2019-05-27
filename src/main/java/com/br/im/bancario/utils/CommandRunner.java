package com.br.im.bancario.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.br.im.bancario.models.Agencia;
import com.br.im.bancario.repositories.AgenciaRepository;

@Configuration
public class CommandRunner implements CommandLineRunner {
	
	@Autowired
	private AgenciaRepository agenciaRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Agencia a = new Agencia();
		a.setNome("Nubank");
		a.setNumero(123L);
		
		agenciaRepository.save(a);
		
	}

}

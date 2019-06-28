package com.algaworks.algamoney.api.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Lancamento salvar(@Valid Lancamento lancamento) {
		
		Pessoa pessoaExample = new Pessoa();
		pessoaExample.setCodigo(lancamento.getPessoa().getCodigo());
		pessoaExample.setAtivo(true);
		
		Optional<Pessoa> pessoa = pessoaRepository.findOne(Example.of(pessoaExample));
		if (pessoa.isPresent()) {

			return lancamentoRepository.save(lancamento);
		}
		
		throw new PessoaInexistenteOuInativaException(); 
	}
	
}

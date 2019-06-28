package com.algaworks.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {

		String[] propriedadesIgnoradas = new String[] {"codigo"};
		return atualizarPessoa(codigo, pessoa, propriedadesIgnoradas);
	}

	public Pessoa atualizarEndereco(Long codigo, Pessoa pessoa) {

		String[] propriedadesIgnoradas = new String[] {"codigo", "nome", "ativo"};
		return atualizarPessoa(codigo, pessoa, propriedadesIgnoradas);
	}

	public Pessoa atualizarPropriedadeAtivo(Long codigo, Pessoa pessoa) {

		String[] propriedadesIgnoradas = new String[] {"codigo", "nome", "endereco"};
		return atualizarPessoa(codigo, pessoa, propriedadesIgnoradas);
	}

	private Pessoa atualizarPessoa(Long codigo, Pessoa pessoa, String[] propriedadesIgnoradas) {
		
		Pessoa pessoaSalva = buscarPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, propriedadesIgnoradas);
		return pessoaRepository.save(pessoaSalva);
	}
	
	private Pessoa buscarPeloCodigo(Long codigo) {

		Pessoa pessoaSalva = pessoaRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return pessoaSalva;
	}
	
}

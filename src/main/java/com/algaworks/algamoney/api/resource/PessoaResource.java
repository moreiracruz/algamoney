package com.algaworks.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.event.CreatedResourceEvent;
import com.algaworks.algamoney.api.model.Endereco;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	private List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	@PostMapping
	private ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		// Não é preciso isto para adicionar o header location na versão nova do spring,
		// Mas pensei ser util se quisesse enviar um email automaticamente apos a
		// criacao
		publisher.publishEvent(new CreatedResourceEvent(this, response, pessoaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("/{codigo}")
	private ResponseEntity<Object> buscarPeloCodigo(@PathVariable Long codigo) {

		Optional<Pessoa> pessoa = this.pessoaRepository.findById(codigo);
		if (pessoa.isPresent()) {
			
			return ResponseEntity.ok(pessoa.get());
		}
		
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void apagar(@PathVariable Long codigo) {
		this.pessoaRepository.deleteById(codigo);
	}

	@PutMapping("/{codigo}")
	private ResponseEntity<Object> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
		
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		
		return ResponseEntity.ok(pessoaSalva);
	}

	@PutMapping("/{codigo}/endereco")
	private ResponseEntity<Object> atualizarEndereco(@PathVariable Long codigo, @Valid @RequestBody Endereco endereco) {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setEndereco(endereco);
		
		Pessoa pessoaSalva = pessoaService.atualizarEndereco(codigo, pessoa);
		
		return ResponseEntity.ok(pessoaSalva);
	}

	@PutMapping("/{codigo}/ativo")
	private ResponseEntity<Object> atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setAtivo(ativo);
		
		Pessoa pessoaSalva = pessoaService.atualizarPropriedadeAtivo(codigo, pessoa);
		
		return ResponseEntity.ok(pessoaSalva);
	}

}

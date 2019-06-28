package com.algaworks.algamoney.api.resource;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
//	@GetMapping
//	public List<Categoria> listar() {
//		return categoriaRepository.findAll();
//	}

	@GetMapping
	public ResponseEntity<?> listar() {
		
		Collection<Categoria> categorias = categoriaRepository.findAll();
		
		return categorias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categorias); 
	}
	
	@PostMapping
//	@ResponseStatus(code = HttpStatus.CREATED)	-- retirado pois ao incluir o ResponseEntity ele já informa o status
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		
		Categoria categriaSalva = categoriaRepository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(categriaSalva.getCodigo()).toUri();
//		response.addHeader("Location", uri.toASCIIString()); -- não é mais necessário na versão nova do Spring
		
		return ResponseEntity.created(uri).body(categriaSalva);
	}
	
//	@GetMapping(path = "/{codigo}")
//	public Categoria buscarPeloCodigo(@PathVariable Long codigo) {
//		
//		return categoriaRepository.findById(codigo).orElse(null);
//	}

//	-- Utilizando Map 
//	@GetMapping(path = "/{codigo}")
//	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
//		
//		return categoriaRepository.findById(codigo)
//				.map(categoria -> ResponseEntity.ok(categoria))
//				.orElse(ResponseEntity.notFound().build());
//	}

//	-- Utilizando isPresent() 
	@GetMapping(path = "/{codigo}")
	public ResponseEntity<Object> buscarPeloCodigo(@PathVariable Long codigo) {
		
	    Optional<Categoria> categoria = this.categoriaRepository.findById(codigo);
	    if (categoria.isPresent())
			return ResponseEntity.ok(categoria.get());
		else
			return ResponseEntity.notFound().build();
	}

}

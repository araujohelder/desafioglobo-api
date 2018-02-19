package com.desafiogloboapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafiogloboapi.model.Contato;
import com.desafiogloboapi.repository.contato.filtro.ContatoFiltro;
import com.desafiogloboapi.service.ContatoService;

@RestController
@RequestMapping("/contato")
public class ContatoResource {
	
	@Autowired
	private ContatoService contatoService;
	
	@GetMapping
	public List<Contato> consultar()  {
		return  contatoService.consultarTodos();
	}
	
	@GetMapping("/pesquisar")
	public Page<Contato> search( ContatoFiltro contatoFiltro, Pageable pageable)  {
		return  contatoService.pesquisar(contatoFiltro, pageable);
	}
	
	@PostMapping
	public ResponseEntity<Contato> save(@Validated @RequestBody Contato contato) {
		Contato contatoCriado =  contatoService.save(contato);
		return ResponseEntity.status(HttpStatus.CREATED).body(contatoCriado);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Contato> findById(@PathVariable Integer id){
		Contato contato = contatoService.findByid(id);
		return contato != null ? ResponseEntity.ok(contato) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Contato> delete(@PathVariable Integer id){
		contatoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Contato>update(@PathVariable Integer id, @Validated @RequestBody Contato contato) {
		Contato contatoSalvo = contatoService.update(id, contato);
		return ResponseEntity.ok().body(contatoSalvo);
	}

}

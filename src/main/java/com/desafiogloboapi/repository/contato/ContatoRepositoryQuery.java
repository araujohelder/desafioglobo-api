package com.desafiogloboapi.repository.contato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.desafiogloboapi.model.Contato;
import com.desafiogloboapi.repository.contato.filtro.ContatoFiltro;


public interface ContatoRepositoryQuery {
	
	public Page<Contato> pesquisar(ContatoFiltro contatoFiltro, Pageable pageable);

}

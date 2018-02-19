package com.desafiogloboapi.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.desafiogloboapi.model.Contato;
import com.desafiogloboapi.repository.ContatoRepository;
import com.desafiogloboapi.repository.contato.filtro.ContatoFiltro;

@Service
public class ContatoService {
	
	
	@Autowired
	private ContatoRepository contatoRepository;
	
	public Contato findByid(Integer id) {
		Contato contato = contatoRepository.findOne(id);
		if (contato == null ) {
			throw new EmptyResultDataAccessException(1);
		}
		return contato;
	}
	
	public List<Contato> consultarTodos() {
		 return  contatoRepository.findAll();
	}
	
	public Page<Contato> pesquisar(ContatoFiltro contatoFiltro, Pageable pageable) {
		Page<Contato> contatos = contatoRepository.pesquisar(contatoFiltro, pageable);
		return contatos;
	} 
	
	public void delete(Integer id) {
		Contato contato = this.findByid(id);
		contatoRepository.delete(contato);
	}

	public Contato save(Contato employee)  {
		return contatoRepository.save(employee);
	}

	public Contato update(Integer id, Contato contato) {
		Contato contatoRetornado = this.findByid(id);
		BeanUtils.copyProperties(contato, contatoRetornado, "id");
		this.contatoRepository.save(contatoRetornado);
		return contatoRetornado;
	}

}

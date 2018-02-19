package com.desafiogloboapi.repository.contato;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.desafiogloboapi.model.Contato;
import com.desafiogloboapi.repository.contato.filtro.ContatoFiltro;



public class ContatoRepositoryImpl implements ContatoRepositoryQuery{

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public Page<Contato> pesquisar(ContatoFiltro contatoFiltro, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Contato> criteria = builder.createQuery(Contato.class);		
		Root<Contato> root = criteria.from(Contato.class);
		Predicate[] predicate = where(contatoFiltro, builder, root);
		criteria.where(predicate);
		criteria.orderBy(builder.asc(root.get("nome")));
		TypedQuery<Contato> query = entityManager.createQuery(criteria);
		
		adicionarRestricaoDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(contatoFiltro)) ;
	}

	

	private Predicate[] where(ContatoFiltro contatoFiltro, CriteriaBuilder builder, Root<Contato> root) {
		
		List<Predicate> predicates = new ArrayList<>();	
		if (!StringUtils.isEmpty(contatoFiltro.getNome())) {
			predicates.add(builder.like(
				builder.lower(root.get("nome")), contatoFiltro.getNome().toLowerCase() + "%" ));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	
	private void adicionarRestricaoDePaginacao(TypedQuery<Contato> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroResgistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		query.setFirstResult(primeiroResgistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(ContatoFiltro contatoFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Contato> root = criteria.from(Contato.class);
		
		Predicate[] predicates = where(contatoFiltro, builder, root);
		criteria.where(predicates);	
		criteria.select(builder.count(root));
		return entityManager.createQuery(criteria).getSingleResult();
	}
}

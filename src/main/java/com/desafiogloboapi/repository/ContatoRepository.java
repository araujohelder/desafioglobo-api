package com.desafiogloboapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafiogloboapi.model.Contato;
import com.desafiogloboapi.repository.contato.ContatoRepositoryQuery;

public interface ContatoRepository extends JpaRepository<Contato, Integer>, ContatoRepositoryQuery{

}

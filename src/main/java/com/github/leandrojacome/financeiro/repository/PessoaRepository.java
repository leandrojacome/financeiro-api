package com.github.leandrojacome.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.leandrojacome.financeiro.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}

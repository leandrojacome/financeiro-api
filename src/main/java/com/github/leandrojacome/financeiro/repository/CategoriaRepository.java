package com.github.leandrojacome.financeiro.repository;

import com.github.leandrojacome.financeiro.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}

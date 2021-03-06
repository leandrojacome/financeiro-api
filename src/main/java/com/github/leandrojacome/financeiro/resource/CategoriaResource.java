package com.github.leandrojacome.financeiro.resource;

import static java.util.Objects.nonNull;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.leandrojacome.financeiro.event.RecursoCriadoEvent;
import com.github.leandrojacome.financeiro.model.Categoria;
import com.github.leandrojacome.financeiro.repository.CategoriaRepository;

@RestController
@RequestMapping("categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> cria(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
        Categoria categoria = categoriaRepository.findOne(id);
        return nonNull(categoria) ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
		categoriaRepository.delete(id);
	}

}

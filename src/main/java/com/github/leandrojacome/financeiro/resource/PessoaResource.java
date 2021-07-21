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
import com.github.leandrojacome.financeiro.model.Pessoa;
import com.github.leandrojacome.financeiro.repository.PessoaRepository;

@RestController
@RequestMapping("pessoa")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> cria(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
        Pessoa pessoa = pessoaRepository.findOne(id);
        return nonNull(pessoa) ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
    	pessoaRepository.delete(id);
	}

}

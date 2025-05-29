package com.rockettest.biblioetca.controller;

import com.rockettest.biblioetca.dto.AuthorRequest;
import com.rockettest.biblioetca.model.Author;
import com.rockettest.biblioetca.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;
import com.rockettest.biblioetca.util.HtmlSanitizer;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    /** Listar o buscar */
    @GetMapping
    public List<Author> getAll(@RequestParam(value = "name", required = false) String name) {
        if (StringUtils.hasText(name)) {
            name = HtmlSanitizer.stripHtml(name);
            return service.findByName(name);
        }
        return service.findAll();
    }

    /** Obtener un autor por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable Short id) {
        Author author = service.findById(id);
        return author != null
            ? ResponseEntity.ok(author)
            : ResponseEntity.notFound().build();
    }

    /** Crear un nuevo autor con validación */
    @PostMapping
    public ResponseEntity<Author> create(@Valid @RequestBody AuthorRequest request) {
        Author author = new Author();
        author.setName( HtmlSanitizer.stripHtml(request.getName()) );
        author.setBiography(HtmlSanitizer.stripHtml(request.getBiography()));
        author.setCreatedAt( new java.sql.Timestamp(System.currentTimeMillis()) );

        Author created = service.create(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Actualizar un autor existente */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Short id, @Valid @RequestBody AuthorRequest request) {
        Author author = new Author();
        author.setId(id);
        author.setName( HtmlSanitizer.stripHtml(request.getName()) );
        author.setBiography(HtmlSanitizer.stripHtml(request.getBiography()));

        service.update(author);
        return ResponseEntity.noContent().build();
    }

    /** Eliminar un autor */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.rockettest.biblioetca.controller;

import com.rockettest.biblioetca.dto.PublisherRequest;
import com.rockettest.biblioetca.model.Publisher;
import com.rockettest.biblioetca.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.rockettest.biblioetca.util.HtmlSanitizer;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@CrossOrigin(origins = "http://localhost:4200")
public class PublisherController {

    private final PublisherService service;

    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @GetMapping
    public List<Publisher> getAll(@RequestParam(value = "name", required = false) String name) {
        if (StringUtils.hasText(name)) {
            name = HtmlSanitizer.stripHtml(name);
            return service.findByName(name);
        }
        return service.findAll();
    }

    /** Obtener una editorial por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getById(@PathVariable Short id) {
        Publisher pub = service.findById(id);
        return pub != null
            ? ResponseEntity.ok(pub)
            : ResponseEntity.notFound().build();
    }

    /** Crear una nueva editorial con validación */
    @PostMapping
    public ResponseEntity<Publisher> create(@Valid @RequestBody PublisherRequest request) {
        Publisher pub = new Publisher();
        pub.setName( HtmlSanitizer.stripHtml(request.getName()) );
        pub.setPhone(HtmlSanitizer.stripHtml(request.getPhone()) );
        pub.setEmail(HtmlSanitizer.stripHtml(request.getEmail()) );
        pub.setAddress(HtmlSanitizer.stripHtml(request.getAddress()) );
        pub.setCreatedAt( new java.sql.Timestamp(System.currentTimeMillis()) );

        Publisher created = service.create(pub);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Actualizar una editorial existente */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Short id, @Valid @RequestBody PublisherRequest request) {
        Publisher pub = new Publisher();
        pub.setId(id);
        pub.setName( HtmlSanitizer.stripHtml(request.getName()) );
        pub.setPhone(HtmlSanitizer.stripHtml(request.getPhone()) );
        pub.setEmail(HtmlSanitizer.stripHtml(request.getEmail()) );
        pub.setAddress(HtmlSanitizer.stripHtml(request.getAddress()) );
        pub.setAddress(request.getAddress());

        service.update(pub);
        return ResponseEntity.noContent().build();
    }

    /** Eliminar una editorial */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

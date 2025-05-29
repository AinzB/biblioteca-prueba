// src/main/java/com/rockettest/biblioetca/controller/BookCopyController.java
package com.rockettest.biblioetca.controller;

import com.rockettest.biblioetca.dto.BookCopyRequest;
import com.rockettest.biblioetca.dto.response.BookCopyResponse;
import com.rockettest.biblioetca.model.BookCopy;
import com.rockettest.biblioetca.service.BookCopyService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bookcopies")
@CrossOrigin(origins = "http://localhost:4200")
public class BookCopyController {

    private final BookCopyService service;

    public BookCopyController(BookCopyService service) {
        this.service = service;
    }

    /** Listar todas las copias con detalles */
    @GetMapping
    public List<BookCopyResponse> getAll(
        @RequestParam(value = "title", required = false) String title
    ) {
        if (title != null && !title.isEmpty()) {
            return service.searchByBookTitle(title);
        }
        return service.findAllWithDetails();
    }

    /** Obtener una copia por ID con detalles */
    @GetMapping("/{id}")
    public ResponseEntity<BookCopyResponse> getById(@PathVariable Short id) {
        BookCopyResponse resp = service.findResponseById(id);
        return resp != null
            ? ResponseEntity.ok(resp)
            : ResponseEntity.notFound().build();
    }

    /** Buscar copias de un libro específico */
    @GetMapping("/book/{bookId}")
    public List<BookCopyResponse> getByBook(@PathVariable Short bookId) {
        return service.findByBookId(bookId);
    }

    /** Crear una nueva copia con validación */
    @PostMapping
    public ResponseEntity<BookCopyResponse> create(@Valid @RequestBody BookCopyRequest request) {
        BookCopy copy = new BookCopy();
        copy.setBookId(request.getBookId());
        copy.setPublisherId(request.getPublisherId());
        copy.setStatus(request.getStatus());
        copy.setCondition(request.getCondition());

        BookCopy created = service.create(copy);
        BookCopyResponse resp = service.findResponseById(created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    /** Actualizar una copia existente */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Short id,
            @Valid @RequestBody BookCopyRequest request
    ) {
        BookCopy copy = new BookCopy();
        copy.setId(id);
        copy.setBookId(request.getBookId());
        copy.setPublisherId(request.getPublisherId());
        copy.setStatus(request.getStatus());
        copy.setCondition(request.getCondition());

        service.update(copy);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/available")
    public ResponseEntity<Void> markAsDisponible(@PathVariable("id") Short copyId) {
        service.markAsDisponible(copyId);
        return ResponseEntity.noContent().build();
    }

    /** Eliminar una copia */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

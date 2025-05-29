// src/main/java/com/rockettest/biblioetca/controller/BookLoanController.java
package com.rockettest.biblioetca.controller;

import com.rockettest.biblioetca.dto.BookLoanRequest;
import com.rockettest.biblioetca.dto.response.BookLoanResponse;
import com.rockettest.biblioetca.model.BookLoan;
import com.rockettest.biblioetca.service.BookLoanService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bookloans")
@CrossOrigin(origins = "http://localhost:4200")
public class BookLoanController {

    private final BookLoanService service;

    public BookLoanController(BookLoanService service) {
        this.service = service;
    }

    @GetMapping
     public List<BookLoanResponse> getAll() {
        return service.findAllWithDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookLoanResponse> getById(@PathVariable Short id) {
        BookLoanResponse resp = service.findResponseById(id);
        return resp != null
        ? ResponseEntity.ok(resp)
        : ResponseEntity.notFound().build();
    }

    @GetMapping("/copy/{copyId}")
    public ResponseEntity<BookLoanResponse> getByCopyId(@PathVariable Short copyId) {
        BookLoanResponse resp = service.findByBookCopyId(copyId);
        return resp != null
        ? ResponseEntity.ok(resp)
        : ResponseEntity.notFound().build();
    }

    @GetMapping("/book/{bookId}")
    public List<BookLoanResponse> getByBookId(@PathVariable Short bookId) {
        return service.findByBookId(bookId);
    }

    /** Crear un nuevo préstamo */
    @PostMapping
    public ResponseEntity<BookLoanResponse> create(@Valid @RequestBody BookLoanRequest req) {
        BookLoan loan = new BookLoan();
        loan.setBookCopyId(req.getBookCopyId());
        loan.setStudentId(req.getStudentId());
        loan.setLoanDate( new java.sql.Timestamp(System.currentTimeMillis()) );
        loan.setDueDate(req.getDueDate());
        loan.setReturnDate(req.getReturnDate());

        BookLoan created = service.create(loan);
        BookLoanResponse resp = service.findResponseById(created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    /** Actualizar un préstamo existente */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
        @PathVariable Short id,
        @Valid @RequestBody BookLoanRequest req
    ) {
        BookLoan loan = new BookLoan();
        loan.setId(id);
        loan.setBookCopyId(req.getBookCopyId());
        loan.setStudentId(req.getStudentId());
        loan.setDueDate(req.getDueDate());
        loan.setReturnDate(req.getReturnDate());

        service.update(loan);
        return ResponseEntity.noContent().build();
    }

    /** Eliminar un préstamo */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

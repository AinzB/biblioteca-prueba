package com.rockettest.biblioetca.controller;

import com.rockettest.biblioetca.dto.BookRequest;
import com.rockettest.biblioetca.dto.BookResponse;
import com.rockettest.biblioetca.model.Book;
import com.rockettest.biblioetca.service.BookService;
import com.rockettest.biblioetca.util.HtmlSanitizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private final BookService service;
    private final Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear carpeta de uploads", e);
        }
    }

    @GetMapping
    public List<BookResponse> getAll(
            @RequestParam(value = "title", required = false) String title
    ) {
        if (title != null && !title.isEmpty()) {
            return service.searchByTitleWithPublisherName(title);
        }
        return service.findAllWithPublisherName();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable Short id) {
        BookResponse resp = service.findResponseById(id);
        return (resp != null)
                ? ResponseEntity.ok(resp)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(
            @Valid @RequestBody BookRequest request
    ) {
        Book book = new Book();
        book.setTitle( HtmlSanitizer.stripHtml(request.getTitle()) );
        book.setIsbn( HtmlSanitizer.stripHtml(request.getIsbn()) );
        book.setCategory( HtmlSanitizer.stripHtml(request.getCategory()) );
        book.setPublicationYear(request.getPublicationYear());
        book.setImageUrl( HtmlSanitizer.stripHtml(request.getImageUrl()) );
        book.setPublisherId( request.getPublisherId() );
        book.setAuthorId( request.getAuthorId() );
        book.setCreatedAt( new Timestamp(System.currentTimeMillis()) );

        Book created = service.create(book);
        BookResponse resp = service.findResponseById(created.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Short id,
            @Valid @RequestBody BookRequest request
    ) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(       HtmlSanitizer.stripHtml(request.getTitle())       );
        book.setIsbn(        HtmlSanitizer.stripHtml(request.getIsbn())        );
        book.setCategory(    HtmlSanitizer.stripHtml(request.getCategory())    );
        book.setPublicationYear(request.getPublicationYear());
        book.setImageUrl(    HtmlSanitizer.stripHtml(request.getImageUrl())    );
        book.setPublisherId( request.getPublisherId()                        );
        book.setAuthorId(    request.getAuthorId()                           );

        service.update(book);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public List<BookResponse> filterByBookAndPublisher(
            @RequestParam Short bookId,
            @RequestParam Short publisherId
    ) {
        return service.findByBookAndPublisher(bookId, publisherId);
    }

    @PostMapping(
        path = "/uploadImage",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String filename = UUID.randomUUID() + "-" + StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                return ResponseEntity.badRequest().body("Nombre de archivo inválido");
            }
            Path target = uploadDir.resolve(filename);
            file.transferTo(target.toFile());
            return ResponseEntity.ok("/images/" + filename);
        } catch (IOException e) {
            logger.error("Error guardando imagen", e);
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al guardar imagen: " + e.getMessage());
        }
    }
}

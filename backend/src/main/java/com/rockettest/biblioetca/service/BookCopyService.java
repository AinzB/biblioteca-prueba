// src/main/java/com/rockettest/biblioetca/service/BookCopyService.java
package com.rockettest.biblioetca.service;

import com.rockettest.biblioetca.dto.response.BookCopyResponse;
import com.rockettest.biblioetca.mapper.BookCopyMapper;
import com.rockettest.biblioetca.mapper.BookMapper;
import com.rockettest.biblioetca.model.BookCopy;
import com.rockettest.biblioetca.model.BookCopyExample;
import com.rockettest.biblioetca.model.BookExample;
import com.rockettest.biblioetca.model.Publisher;
import com.rockettest.biblioetca.model.Author;
import com.rockettest.biblioetca.model.Book;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookCopyService {
    private final BookCopyMapper mapper;
    private final PublisherService publisherService;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    public BookCopyService(
            BookCopyMapper mapper,
            PublisherService publisherService,
            BookMapper bookMapper,
            AuthorService authorService
    ) {
        this.mapper = mapper;
        this.publisherService = publisherService;
        this.bookMapper = bookMapper;
        this.authorService = authorService;
    }

    /** Devuelve todas las copias de libro puras */
    public List<BookCopy> findAll() {
        return mapper.selectByExample(null);
    }

    /** Devuelve todas las copias con detalles de libro y editorial */
    public List<BookCopyResponse> findAllWithDetails() {
        List<BookCopy> copies = mapper.selectByExample(null);
        return copies.stream()
            .map(this::toResponseWithDetails)
            .collect(Collectors.toList());
    }

    /** Busca copias por bookId con detalles */
    public List<BookCopyResponse> findByBookId(Short bookId) {
        BookCopyExample example = new BookCopyExample();
        example.createCriteria().andBookIdEqualTo(bookId);
        return mapper.selectByExample(example).stream()
            .map(this::toResponseWithDetails)
            .collect(Collectors.toList());
    }

    public List<BookCopyResponse> searchByBookTitle(String titleFragment) {
        // 1) Buscar Books cuyo title LIKE %titleFragment%
        BookExample bookEx = new BookExample();
        bookEx.createCriteria()
            .andTitleLike("%" + titleFragment + "%");
        List<Book> matchingBooks = bookMapper.selectByExample(bookEx);

        if (matchingBooks.isEmpty()) {
            return Collections.emptyList();
        }

        // 2) Extraer sus IDs
        List<Short> bookIds = matchingBooks.stream()
                                            .map(Book::getId)
                                            .collect(Collectors.toList());

        // 3) Buscar BookCopy por esos bookIds
        BookCopyExample copyEx = new BookCopyExample();
        copyEx.createCriteria()
            .andBookIdIn(bookIds);
        List<BookCopy> copies = mapper.selectByExample(copyEx);

        // 4) Transformar a DTO con detalles
        return copies.stream()
                    .map(this::toResponseWithDetails)
                    .collect(Collectors.toList());
    }

    /** Devuelve una copia pura por su ID */
    public BookCopy findById(Short id) {
        return mapper.selectByPrimaryKey(id);
    }

    /** Devuelve una copia con detalles por su ID, o null */
    public BookCopyResponse findResponseById(Short id) {
        BookCopy copy = mapper.selectByPrimaryKey(id);
        return copy != null
            ? toResponseWithDetails(copy)
            : null;
    }

    /** Crea una nueva copia */
    public BookCopy create(BookCopy copy) {
        mapper.insert(copy);
        return copy;
    }

    /** Actualiza una copia existente */
    public void update(BookCopy copy) {
        mapper.updateByPrimaryKeySelective(copy);
    }

    public void markAsPrestado(Short copyId) {
        BookCopy copy = mapper.selectByPrimaryKey(copyId);
        if (copy != null) {
            copy.setStatus("PRESTADO");
            mapper.updateByPrimaryKeySelective(copy);
        }
    }

    public void markAsDisponible(Short copyId) {
        BookCopy copy = mapper.selectByPrimaryKey(copyId);
        if (copy != null) {
            copy.setStatus("DISPONIBLE");
            mapper.updateByPrimaryKeySelective(copy);
        }
    }

    /** Elimina una copia por su ID */
    public void delete(Short id) {
        mapper.deleteByPrimaryKey(id);
    }

    private BookCopyResponse toResponseWithDetails(BookCopy copy) {
        BookCopyResponse dto = new BookCopyResponse();
        BeanUtils.copyProperties(copy, dto);

        // 1. Libro
        Book b = bookMapper.selectByPrimaryKey(copy.getBookId());
        if (b != null) {
            dto.setBookTitle(b.getTitle());
            dto.setBookImageUrl(b.getImageUrl());
            // autor desde el Book
            dto.setAuthorId(b.getAuthorId());
            Author a = authorService.findById(b.getAuthorId());
            dto.setAuthorName(a != null ? a.getName() : null);
        }

        // 2. Editorial
        Publisher p = publisherService.findById(copy.getPublisherId());
        dto.setPublisherName(p != null ? p.getName() : null);

        return dto;
    }

    public Map<String,Integer> countByCondition(Short bookId, Short publisherId) {
        BookCopyExample example = new BookCopyExample();
        example.createCriteria()
               .andBookIdEqualTo(bookId)
               .andPublisherIdEqualTo(publisherId);

        example.clear();
        example.createCriteria()
               .andBookIdEqualTo(bookId)
               .andPublisherIdEqualTo(publisherId)
               .andConditionEqualTo("EXCELENTE");
        int excelente = (int)mapper.countByExample(example);

        example.clear();
        example.createCriteria()
               .andBookIdEqualTo(bookId)
               .andPublisherIdEqualTo(publisherId)
               .andConditionEqualTo("BUENA");
        int buena = (int)mapper.countByExample(example);

        example.clear();
        example.createCriteria()
               .andBookIdEqualTo(bookId)
               .andPublisherIdEqualTo(publisherId)
               .andConditionEqualTo("MALA");
        int mala = (int)mapper.countByExample(example);

        Map<String,Integer> counts = new HashMap<>();
        counts.put("EXCELENTE", excelente);
        counts.put("BUENA",     buena);
        counts.put("MALA",      mala);
        return counts;
    }
}

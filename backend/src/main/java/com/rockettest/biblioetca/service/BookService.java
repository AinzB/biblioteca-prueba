package com.rockettest.biblioetca.service;

import com.rockettest.biblioetca.dto.BookResponse;
import com.rockettest.biblioetca.mapper.BookMapper;
import com.rockettest.biblioetca.model.Book;
import com.rockettest.biblioetca.model.BookExample;
import com.rockettest.biblioetca.model.Publisher;
import com.rockettest.biblioetca.model.Author;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookMapper mapper;
    private final PublisherService publisherService;
    private final AuthorService authorService;
    private final BookCopyService bookCopyService; 

    public BookService(BookMapper mapper,
                       PublisherService publisherService,
                       AuthorService authorService,
                       BookCopyService bookCopyService
                    ) {
        this.mapper = mapper;
        this.publisherService = publisherService;
        this.authorService = authorService;
        this.bookCopyService = bookCopyService;
    }

    /** Devuelve todos los Book puros */
    public List<Book> findAll() {
        return mapper.selectByExample(null);
    }

    /** Devuelve todos los libros con nombre de editorial y de autor */
    public List<BookResponse> findAllWithPublisherName() {
        List<Book> books = mapper.selectByExample(null);
        return books.stream()
                .map(this::toResponseWithNames)
                .collect(Collectors.toList());
    }

    public List<BookResponse> searchByTitleWithPublisherName(String title) {
        BookExample example = new BookExample();
        example.createCriteria().andTitleLike("%" + title + "%");
        List<Book> books = mapper.selectByExample(example);
        return books.stream()
                .map(this::toResponseWithNames)
                .collect(Collectors.toList());
    }

    public Book findById(Short id) {
        return mapper.selectByPrimaryKey(id);
    }

    public BookResponse findResponseById(Short id) {
        Book book = mapper.selectByPrimaryKey(id);
        return book != null ? toResponseWithNames(book) : null;
    }

    public Book create(Book book) {
        mapper.insert(book);
        return book;
    }

    public void update(Book book) {
        mapper.updateByPrimaryKeySelective(book);
    }

    public void delete(Short id) {
        mapper.deleteByPrimaryKey(id);
    }

    public List<BookResponse> findByBookAndPublisher(Short bookId, Short publisherId) {
        BookExample example = new BookExample();
        example.createCriteria()
            .andIdEqualTo(bookId)
            .andPublisherIdEqualTo(publisherId);

        List<Book> books = mapper.selectByExample(example);
        return books.stream()
                    .map(this::toResponseWithNames)
                    .collect(Collectors.toList());
    }

    private BookResponse toResponseWithNames(Book book) {
        BookResponse dto = new BookResponse();
        BeanUtils.copyProperties(book, dto);

        // 2) Nombre de la editorial
        String pubName = publisherService
            .findAll()
            .stream()
            .filter(p -> p.getId().equals(book.getPublisherId()))
            .map(Publisher::getName)
            .findFirst()
            .orElse("—");
        dto.setPublisherName(pubName);

        // 3) Nombre del autor
        String authName = authorService
            .findAll()
            .stream()
            .filter(a -> a.getId().equals(book.getAuthorId()))
            .map(Author::getName)
            .findFirst()
            .orElse("—");
        dto.setAuthorName(authName);

        // 4) Conteos de copias por condición
        Map<String,Integer> counts = bookCopyService.countByCondition(
            book.getId(), book.getPublisherId()
        );
        dto.setCountExcelente(counts.getOrDefault("EXCELENTE", 0));
        dto.setCountBuena(    counts.getOrDefault("BUENA", 0));
        dto.setCountMala(     counts.getOrDefault("MALA", 0));

        return dto;
    }

}

// src/main/java/com/rockettest/biblioetca/service/BookLoanService.java
package com.rockettest.biblioetca.service;

import com.rockettest.biblioetca.dto.response.BookLoanResponse;
import com.rockettest.biblioetca.mapper.BookLoanMapper;
import com.rockettest.biblioetca.mapper.BookCopyMapper;
import com.rockettest.biblioetca.mapper.BookMapper;
import com.rockettest.biblioetca.mapper.StudentMapper;
import com.rockettest.biblioetca.model.BookLoan;
import com.rockettest.biblioetca.model.BookLoanExample;
import com.rockettest.biblioetca.model.BookCopy;
import com.rockettest.biblioetca.model.BookCopyExample;
import com.rockettest.biblioetca.model.Book;
import com.rockettest.biblioetca.model.Student;
import com.rockettest.biblioetca.model.Publisher;
import com.rockettest.biblioetca.model.Author;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookLoanService {
  private final BookLoanMapper    loanMapper;
  private final BookCopyMapper    copyMapper;
  private final BookMapper        bookMapper;
  private final StudentMapper     studentMapper;
  private final PublisherService  publisherService;
  private final AuthorService     authorService;
  private final BookCopyService   bookCopyService;

  public BookLoanService(
      BookLoanMapper loanMapper,
      BookCopyMapper copyMapper,
      BookMapper bookMapper,
      StudentMapper studentMapper,
      PublisherService publisherService,
      AuthorService authorService,
      BookCopyService bookCopyService
  ) {
      this.loanMapper       = loanMapper;
      this.copyMapper       = copyMapper;
      this.bookMapper       = bookMapper;
      this.studentMapper    = studentMapper;
      this.publisherService = publisherService;
      this.authorService    = authorService;
      this.bookCopyService  = bookCopyService;
  }

  public List<BookLoan> findAll() {
      return loanMapper.selectByExample(null);
  }

  public BookLoan findById(Short id) {
      return loanMapper.selectByPrimaryKey(id);
  }

    /**
   * Busca el préstamo asociado a una copia de libro.
   * @param copyId el ID de la copia de libro
   * @return el BookLoanResponse enriquecido, o null si no existe
   */
  public BookLoanResponse findByBookCopyId(Short copyId) {
    BookLoanExample ex = new BookLoanExample();
    ex.createCriteria().andBookCopyIdEqualTo(copyId);
    List<BookLoan> list = loanMapper.selectByExample(ex);
    if (list.isEmpty()) {
      return null;
    }
    // Tomar el primero (asumimos un solo préstamo activo por copia)
    return toResponseWithDetails(list.get(0));
  }

  public BookLoan create(BookLoan loan) {
      loanMapper.insert(loan);
      bookCopyService.markAsPrestado(loan.getBookCopyId());
      return loan;
  }

  public void update(BookLoan loan) {
      loanMapper.updateByPrimaryKeySelective(loan);
  }

  public void delete(Short id) {
      loanMapper.deleteByPrimaryKey(id);
  }

  public List<BookLoanResponse> findAllWithDetails() {
      return findAll().stream()
          .map(this::toResponseWithDetails)
          .collect(Collectors.toList());
  }

  public BookLoanResponse findResponseById(Short id) {
      BookLoan loan = findById(id);
      return (loan != null)
          ? toResponseWithDetails(loan)
          : null;
  }

  public List<BookLoanResponse> findByBookId(Short bookId) {
        // 1) Buscar todas las copias de ese libro
        BookCopyExample copyEx = new BookCopyExample();
        copyEx.createCriteria()
            .andBookIdEqualTo(bookId);
        List<BookCopy> copies = copyMapper.selectByExample(copyEx);

        if (copies.isEmpty()) {
            return Collections.emptyList();
        }

        // 2) Extraer los IDs de copia
        List<Short> copyIds = copies.stream()
                                    .map(BookCopy::getId)
                                    .collect(Collectors.toList());

        // 3) Encontrar préstamos para esas copias
        BookLoanExample loanEx = new BookLoanExample();
        loanEx.createCriteria()
            .andBookCopyIdIn(copyIds);
            
        List<BookLoan> loans = loanMapper.selectByExample(loanEx);

        // 4) Mapear a DTO con detalles
        return loans.stream()
                    .map(this::toResponseWithDetails)
                    .collect(Collectors.toList());
    }

  /** Helper: convierte BookLoan en BookLoanResponse con todos los campos extra */
  private BookLoanResponse toResponseWithDetails(BookLoan loan) {
      BookLoanResponse dto = new BookLoanResponse();
      BeanUtils.copyProperties(loan, dto);

      // 1) Cargar BookCopy
      BookCopy copy = copyMapper.selectByPrimaryKey(loan.getBookCopyId());
      if (copy != null) {
          Book book = bookMapper.selectByPrimaryKey(copy.getBookId());
          if (book != null) {
              dto.setBookTitle(book.getTitle());
              dto.setBookImageUrl(book.getImageUrl());

              Author author = authorService.findById(book.getAuthorId());
              dto.setAuthorName(author != null ? author.getName() : null);

              Publisher pub = publisherService.findById(book.getPublisherId());
              dto.setPublisherName(pub != null ? pub.getName() : null);
          }

          dto.setStatus(copy.getStatus());
          dto.setCondition(copy.getCondition());
      }

      Student s = studentMapper.selectByPrimaryKey(loan.getStudentId());
      if (s != null) {
          dto.setStudentName(s.getFirstName() + " " + s.getLastName());
      }

      return dto;
  }
}

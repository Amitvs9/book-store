package com.store.book.service;

import com.store.book.domain.Book;
import com.store.book.repository.BookRepository;
import com.store.book.util.DataFeeder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * The type Payment service.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void loadBooksData() {
        bookRepository.saveAll(DataFeeder.getBooksData());
    }

    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }

  public Book findBookByISBN(String isbn) {

    return bookRepository.findByIsbn(isbn);
  }


}

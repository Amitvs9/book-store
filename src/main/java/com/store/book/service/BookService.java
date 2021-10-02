package com.store.book.service;

import com.store.book.entities.Book;
import com.store.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Book service.
 *
 * @author Amit Vs
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }

    public Book findBookByISBN(String isbn) {

        return bookRepository.findByIsbn(isbn);
    }


}

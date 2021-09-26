package com.store.book.controller;

import com.store.book.domain.Book;
import com.store.book.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Payment controller.
 */
@RestController
@RequestMapping("/bookstore")
@CrossOrigin
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Gets amount paid per user.
     *
     * @return the amount paid per user
     */
    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    /**
     * Gets amount owes by users.
     *
     * @param isbn the user
     * @return the amount owes by users
     */
    @GetMapping(value = "/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBookByIsbn(@PathVariable(name = "isbn") String isbn) {
        return new ResponseEntity<>(bookService.findBookByISBN(isbn), HttpStatus.OK);
    }
}

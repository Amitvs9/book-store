package com.store.book.controller;

import com.store.book.entities.Book;
import com.store.book.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type BookController controller.
 *
 * @author Amit Vs
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
     * Gets all books
     *
     * @return List<Book>
     */
    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    /**
     * Gets Book for given isbn
     *
     * @param isbn the user
     * @return Book
     */
    @GetMapping(value = "/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book getBookByIsbn(@PathVariable(name = "isbn") String isbn) {
        return bookService.findBookByISBN(isbn);
    }
}

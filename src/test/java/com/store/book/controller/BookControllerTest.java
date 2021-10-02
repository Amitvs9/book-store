package com.store.book.controller;

import com.store.book.entities.Book;
import com.store.book.service.BookService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Amit Vs
 */
@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    @InjectMocks
    BookController bookController;

    @Mock
    private BookService bookService;


    @Test
    public void testBooks() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookService.getAllBooks()).thenReturn(formBooks());
        List<Book> books = bookController.getBooks();
        Assert.assertEquals("Book title name is not expected ", "Java Book", books.get(0).getTitle());
        Assert.assertEquals("Book isbn is not expected", "12345678", books.get(0).getIsbn());
        Assert.assertEquals("Book Author is not expected ", "James Gosling", books.get(0).getAuthors());
    }

    @Test
    public void testBooksByISBN() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookService.findBookByISBN(anyString())).thenReturn(formBook());
        Book book = bookController.getBookByIsbn("");
        Assert.assertEquals("Book title name is not expected ", "Java Book", book.getTitle());
        Assert.assertEquals("Book isbn is not expected", "12345678", book.getIsbn());
        Assert.assertEquals("Book Author is not expected ", "James Gosling", book.getAuthors());
    }

    private List<Book> formBooks() {
        return Collections.singletonList(formBook());
    }

    private Book formBook() {
        return Book.builder()
                        .title("Java Book")
                        .authors("James Gosling")
                        .isbn("12345678")
                        .build();
    }
}

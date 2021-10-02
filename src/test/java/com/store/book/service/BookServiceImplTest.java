package com.store.book.service;

import com.store.book.entities.Book;
import com.store.book.repository.BookRepository;
import com.store.book.util.DataFeederTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

/**
 * @author Amit Vs
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;


    @Test
    public void testAllBooks(){
        Mockito.when(bookRepository.findAll()).thenReturn(DataFeederTest.getBooksData());
        List<Book> books =bookService.getAllBooks();
        Assert.assertEquals("Book title name is not expected ", "Java Book", books.get(0).getTitle());
        Assert.assertEquals("Book isbn is not expected", "12345678", books.get(0).getIsbn());
        Assert.assertEquals("Book Author is not expected ", "James Gosling", books.get(0).getAuthors());
        Assert.assertEquals("Book Language is not expected ", "English", books.get(0).getLanguage());
        Assert.assertEquals("Book Price is not expected ", "$100", books.get(0).getPrice());
    }


    @Test
    public void testAllBooksByIsbn(){
        Mockito.when(bookRepository.findByIsbn(anyString())).thenReturn(DataFeederTest.getBooksData().get(0));
        Book book =bookService.findBookByISBN("12345678");
        Assert.assertEquals("Book title name is not expected ", "Java Book", book.getTitle());
        Assert.assertEquals("Book isbn is not expected", "12345678", book.getIsbn());
        Assert.assertEquals("Book Author is not expected ", "James Gosling", book.getAuthors());
        Assert.assertEquals("Book Language is not expected ", "English", book.getLanguage());
        Assert.assertEquals("Book Price is not expected ", "$100", book.getPrice());
    }
}

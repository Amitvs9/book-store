package com.store.book.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.book.entities.Book;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Amit Vs
 */
@UtilityClass
public class DataFeederTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Book> getBooksData() {
        try {
            return objectMapper
                            .readValue(new File(DataFeederTest.class.getClassLoader().getResource("books.json").getFile())
                                            , new TypeReference<List<Book>>() {

                                            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

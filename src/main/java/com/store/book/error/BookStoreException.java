package com.store.book.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Custom exception class for Airport-service exception
 *
 * @author Amit Vs
 */
@Component
@Getter
@NoArgsConstructor
public class BookStoreException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1L;
    private BookStoreError bookStoreError;

    public BookStoreException(String errorCode, String errorDescription){
        super();
        this.bookStoreError = new BookStoreError(errorCode,errorDescription);
    }
}

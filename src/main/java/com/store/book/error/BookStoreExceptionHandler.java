package com.store.book.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Centralize ExceptionHandler for Airport-service
 *
 * @author Amit Vs
 */
@ControllerAdvice
public class BookStoreExceptionHandler {

    @ExceptionHandler({BookStoreException.class})
    @ResponseBody
    public BookStoreError serviceError(HttpServletResponse httpRes, BookStoreException bookStoreException) {

        String errorCode = bookStoreException.getBookStoreError().getErrorCode();

        if (errorCode.equalsIgnoreCase(BookStoreErrorConstants.ERROR_USER_EXIST)) {
            httpRes.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        else if (errorCode.equalsIgnoreCase(BookStoreErrorConstants.ERROR_BAD_CREDENTIALS_E1001)) {
            httpRes.setStatus(HttpStatus.NOT_FOUND.value());
        }
        else {
            httpRes.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new BookStoreException(BookStoreErrorConstants.INTERNAL_ERROR_E1008, BookStoreErrorConstants.INTERNAL_ERROR).getBookStoreError();
        }

        return bookStoreException.getBookStoreError();
    }
}

package com.store.book.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Error model class
 *
 * @author Amit Vs
 */
@Getter
@AllArgsConstructor
public class BookStoreError implements Serializable {


    private static final long serialVersionUID = -8979390207199719926L;

    private String errorCode;
    private String errorMessage;

}

package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.util.HttpStatus;
import com.mateuszwiater.csc435.util.Response;

public class ModelResponse<T> extends Response<HttpStatus, T> {

    public ModelResponse(HttpStatus status, T t, String message) {
        super(status, t, message);
    }
}

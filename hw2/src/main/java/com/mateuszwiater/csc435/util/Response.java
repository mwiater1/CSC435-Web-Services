package com.mateuszwiater.csc435.util;

import java.util.Optional;

public abstract class Response<S,T> {
    private final String message;
    private final Optional<T> data;
    private final S status;

    public Response(final S status, final T t, final String message) {
        this.status = status;
        this.message = message;
        this.data = t == null ? Optional.empty() : Optional.of(t);
    }

    public String getMessage() {
        return message;
    }

    public Optional<T> getData() {
        return data;
    }

    public S getStatus() {
        return status;
    }
}

package com.mateuszwiater.csc435.model;

import java.util.Optional;

public class Model<T> {
    private final Status status;
    private final String msg;
    private final Optional<T> model;

    Model(final T t) {
        this(Status.OK, "", t);
    }

    Model(final Status status, final String msg) {
        this(status, msg, null);
    }

    Model(final Status status, final String msg, final T t) {
        this.status = status;
        this.msg = msg;
        this.model = t == null ? Optional.empty() : Optional.of(t);
    }

    public String getMsg() {
        return msg;
    }

    public Optional<T> getModel() {
        return model;
    }

    public enum Status {
        OK,
        FAIL;

    }
}

package com.mateuszwiater.csc435.util;

import com.google.gson.Gson;

public abstract class Response {
    private static Gson GSON = new Gson();
    private final Status status;
    private final String message;

    public Response(final Status status, final String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String toJson() {
        return GSON.toJson(this);
    }

    public enum Status {
        OK,
        FAIL
    }
}

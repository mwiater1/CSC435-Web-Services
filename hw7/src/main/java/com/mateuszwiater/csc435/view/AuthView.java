package com.mateuszwiater.csc435.view;

import com.mateuszwiater.csc435.util.Response;

public class AuthView extends Response {

    private AuthView(Status status, String message) {
        super(status, message);
    }

    public static String getView(final Status status, final String message) {
        return new AuthView(status, message).toJson();
    }
}

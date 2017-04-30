package com.mateuszwiater.csc435.view;

import com.mateuszwiater.csc435.util.Response;

public class NotFoundView extends Response {

    private NotFoundView(Status status, String message) {
        super(status, message);
    }

    public static String getView(final Status status, final String message) {
        return new NotFoundView(status, message).toJson();
    }
}

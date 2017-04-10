package com.mateuszwiater.csc435.rest.view;

import com.mateuszwiater.csc435.model.User;
import com.mateuszwiater.csc435.rest.util.Response;
import com.mateuszwiater.csc435.rest.util.Response.Status;


public class UserView {

    public static String getView(final Status status, final String message, final User user) {
        return new UserResponse(status, message, user).toJson();
    }

    public static String postView(final Status status, final String message, final User user) {
        return getView(status, message, user);
    }

    public static String putView(final Status status, final String message, final User user) {
        return getView(status, message, user);
    }

    public static String deleteView(final Status status, final String message) {
        return getView(status, message, null);
    }

    private static class UserResponse extends Response {
        final String userName, apiKey;

        UserResponse(final Status status, final String message, final User user) {
            super(status, message);
            this.userName = user != null ? user.getUserName() : null;
            this.apiKey = user != null ? user.getApiKey() : null;
        }
    }
}

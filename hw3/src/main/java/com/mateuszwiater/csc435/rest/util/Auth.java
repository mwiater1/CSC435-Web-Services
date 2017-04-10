package com.mateuszwiater.csc435.rest.util;

import com.mateuszwiater.csc435.model.User;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static com.mateuszwiater.csc435.rest.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.rest.util.Response.Status.OK;

public class Auth {

    public static AuthResponse authenticate(final String apiKey) throws SQLException {
        final Optional<User> user = User.getUser(UUID.fromString(apiKey));
        if(apiKey != null && !apiKey.isEmpty() && user.isPresent()) {
            return new AuthResponse(OK, "", true, user.get());
        } else {
            return new AuthResponse(FAIL, "Unable to authenticate user. Check APIKey and try again.", false, null);
        }
    }

    public static class AuthResponse extends Response {
        private transient boolean isAuthenticated;
        private transient User user;

        public AuthResponse(final Status status, final String message, final boolean isAuthenticated, final User user) {
            super(status, message);
            this.user = user;
            this.isAuthenticated = isAuthenticated;
        }

        public User getUser() {
            return user;
        }

        public boolean isAuthenticated() {
            return isAuthenticated;
        }
    }
}

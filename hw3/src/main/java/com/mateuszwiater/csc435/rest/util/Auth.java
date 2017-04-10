package com.mateuszwiater.csc435.rest.util;

import com.mateuszwiater.csc435.model.User;
import com.mateuszwiater.csc435.rest.view.AuthView;

import java.sql.SQLException;
import java.util.UUID;

import static com.mateuszwiater.csc435.rest.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.rest.util.Response.Status.OK;

public class Auth {

    public static AuthResponse authenticate(String apiKey) throws SQLException {
        try {
            final UUID uuid = UUID.fromString(apiKey);
            return new AuthResponse(User.getUser(uuid).orElse(null));
        } catch (NullPointerException | IllegalArgumentException e) {
            return new AuthResponse(null);
        }
    }

    public static class AuthResponse {
        private User user;

        public AuthResponse(final User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public boolean isAuthenticated() {
            return user != null;
        }

        public String getView() {
            if(isAuthenticated()) {
                return AuthView.getView(OK, "");
            } else {
                return AuthView.getView(FAIL, "Unable to authenticate user. Check APIKey and try again.");
            }
        }
    }
}

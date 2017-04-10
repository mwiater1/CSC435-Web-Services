package com.mateuszwiater.csc435.rest.util;

import com.mateuszwiater.csc435.model.User;
import com.mateuszwiater.csc435.rest.view.AuthView;

import java.sql.SQLException;
import java.util.UUID;

import static com.mateuszwiater.csc435.rest.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.rest.util.Response.Status.OK;

public class Auth {

    public static AuthResponse authenticate(String apiKey) throws SQLException {
        if(apiKey == null || !apiKey.isEmpty()) {
            apiKey = "any";
        }

        return new AuthResponse(User.getUser(UUID.fromString(apiKey)).orElse(null));
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

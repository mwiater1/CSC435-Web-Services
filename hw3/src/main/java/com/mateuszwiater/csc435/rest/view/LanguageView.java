package com.mateuszwiater.csc435.rest.view;

import com.mateuszwiater.csc435.model.Language;
import com.mateuszwiater.csc435.rest.util.Response;
import com.mateuszwiater.csc435.rest.util.Response.Status;

import java.util.List;

public class LanguageView {

    public static String getView(final Status status, final String message, final List<Language> languages) {
        return new LanguageResponse(status, message, languages).toJson();
    }

    private static class LanguageResponse extends Response {
        final List<Language> languages;

        LanguageResponse(final Status status, final String message, final List<Language> languages) {
            super(status, message);
            this.languages = languages;
        }
    }
}

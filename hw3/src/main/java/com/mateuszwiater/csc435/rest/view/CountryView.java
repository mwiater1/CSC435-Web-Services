package com.mateuszwiater.csc435.rest.view;

import com.mateuszwiater.csc435.model.Country;
import com.mateuszwiater.csc435.rest.util.Response;
import com.mateuszwiater.csc435.rest.util.Response.Status;

import java.util.List;

public class CountryView {

    public static String getView(final Status status, final String message, final List<Country> countries) {
        return new CountryResponse(status, message, countries).toJson();
    }

    private static class CountryResponse extends Response {
        final List<Country> countries;

        CountryResponse(final Status status, final String message, final List<Country> countries) {
            super(status, message);
            this.countries = countries;
        }
    }
}

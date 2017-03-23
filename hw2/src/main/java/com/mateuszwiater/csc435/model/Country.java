package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;
import com.mateuszwiater.csc435.db.SqlResponse;
import com.mateuszwiater.csc435.db.SqlStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.mateuszwiater.csc435.util.HttpStatus.INTERNAL_SERVER_ERROR;
import static com.mateuszwiater.csc435.util.HttpStatus.OK;

public class Country {
    private final String name, code;

    private Country(final String code) {
        this.code = code;
        this.name = new Locale("", code).getDisplayCountry();
    }

    public static ModelResponse<List<Country>> getCountries() {
        final String query = "SELECT DISTINCT COUNTRY FROM SOURCES;";

        final SqlResponse res = DatabaseConnector.runQuery(query);

        if(res.getStatus() == SqlStatus.OK) {
            final List<Country> countries = new ArrayList<>();
            res.getData().ifPresent(l -> l.stream().map(l2 -> l2.get(0)).forEach(s -> countries.add(new Country(s))));
            return new ModelResponse<>(OK, countries, "");
        } else {
            return new ModelResponse<>(INTERNAL_SERVER_ERROR, null, "Error retrieving supported countries. Try again.");
        }
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}

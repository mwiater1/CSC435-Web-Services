package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class Country {
    private final String name, code;

    private Country(final String code) {
        this.code = code;
        this.name = new Locale("", code).getDisplayCountry();
    }

    public static Optional<Country> getCountry(final String country) throws SQLException {
        final String query = String.format("SELECT DISTINCT COUNTRY FROM SOURCES WHERE COUNTRY = '%s';", country);

        final Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);

        if (res.isPresent()) {
            return Optional.of(new Country(country));
        } else {
            return Optional.empty();
        }
    }

    public static List<Country> getCountries() throws SQLException {
        final String query = "SELECT DISTINCT COUNTRY FROM SOURCES;";

        final Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);

        if (res.isPresent()) {
            return res.get().stream().map(l -> l.get(0)).map(Country::new).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}

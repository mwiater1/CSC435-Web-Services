package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class Language {
    private final String name, code;

    private Language(final String code) {
        this.code = code;
        this.name = new Locale(code).getDisplayLanguage();
    }

    public static Optional<Language> getLanguage(final String language) throws SQLException {
        final String query = String.format("SELECT DISTINCT LANGUAGE FROM SOURCES WHERE LANGUAGE = '%s';", language);

        final Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);

        if (res.isPresent()) {
            return Optional.of(new Language(language));
        } else {
            return Optional.empty();
        }
    }

    public static List<Language> getLanguages() throws SQLException {
        final String query = "SELECT DISTINCT LANGUAGE FROM SOURCES;";

        final Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);

        if (res.isPresent()) {
            return res.get().stream().map(l -> l.get(0)).map(Language::new).collect(Collectors.toList());
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

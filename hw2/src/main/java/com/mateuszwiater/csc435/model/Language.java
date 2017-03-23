package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;
import com.mateuszwiater.csc435.db.SqlResponse;
import com.mateuszwiater.csc435.db.SqlStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.mateuszwiater.csc435.util.HttpStatus.*;

public class Language {
    private final String name, code;

    private Language(final String code) {
        this.code = code;
        this.name = new Locale(code).getDisplayLanguage();
    }

    public static ModelResponse<List<Language>> getLanguages() {
        final String query = "SELECT DISTINCT LANGUAGE FROM SOURCES;";

        final SqlResponse res = DatabaseConnector.runQuery(query);

        if(res.getStatus() == SqlStatus.OK) {
            final List<Language> languages = new ArrayList<>();
            res.getData().ifPresent(l -> l.stream().map(l2 -> l2.get(0)).forEach(s -> languages.add(new Language(s))));
            return new ModelResponse<>(OK, languages, "");
        } else {
            return new ModelResponse<>(INTERNAL_SERVER_ERROR, null, "Error retrieving supported languages. Try again.");
        }
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}

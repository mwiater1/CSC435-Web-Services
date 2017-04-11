package models;

import java.util.Locale;

public class Language {
    private final String name, code;

    private Language(final String code) {
        this.code = code;
        this.name = new Locale(code).getDisplayLanguage();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}

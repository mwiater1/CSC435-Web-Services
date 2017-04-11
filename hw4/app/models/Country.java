package models;

import java.util.Locale;

public class Country {
    private final String name, code;

    private Country(final String code) {
        this.code = code;
        this.name = new Locale("", code).getDisplayCountry();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}

package models;

import java.util.Arrays;
import java.util.List;
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

    public static List<Country> getCountries() {
        return Arrays.asList(new Country("au"), new Country("us"));
    }
}

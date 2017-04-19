package models;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class Country extends CodeName {

    private Country(final String code) {
        super(new Locale("", code).getDisplayCountry(), code);
    }

    public static Optional<Country> getCountry(final String country) {
        return Optional.ofNullable(Source.find
                .select("country")
                .setDistinct(true)
                .where()
                .eq("country", country)
                .findUnique()
        ).map(s -> new Country(s.getCountry()));
    }

    public static List<Country> getCountries() {
        return Source.find
                .select("country")
                .setDistinct(true)
                .findSet()
                .stream()
                .map(s -> new Country(s.getCountry()))
                .collect(Collectors.toList());
    }
}

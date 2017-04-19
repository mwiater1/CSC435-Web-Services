package models;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class Language extends CodeName {

    private Language(final String code) {
        super(new Locale(code).getDisplayLanguage(), code);
    }

    public static Optional<Language> getLanguage(final String language) {
        return Optional.ofNullable(Source.find
                .select("language")
                .setDistinct(true)
                .where()
                .eq("language", language)
                .findUnique()
        ).map(s -> new Language(s.getLanguage()));
    }

    public static List<Language> getLanguages() {
        return Source.find
                .select("language")
                .setDistinct(true)
                .findSet()
                .stream()
                .map(s -> new Language(s.getLanguage()))
                .collect(Collectors.toList());
    }
}

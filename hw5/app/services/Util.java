package services;

import java.util.Optional;

public class Util {

    public static String orElse(final String string) {
        return orElse(string, "Unknown");
    }

    public static <T> T orElse(final T obj, final T alt) {
        return Optional.ofNullable(obj).orElse(alt);
    }
}

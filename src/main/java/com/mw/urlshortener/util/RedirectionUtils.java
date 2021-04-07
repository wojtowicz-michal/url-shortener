package com.mw.urlshortener.util;

import static com.mw.urlshortener.util.Constants.*;

public final class RedirectionUtils {

    private RedirectionUtils() {}

    public static String removeUrlPrefix(String url) {
        return url.replaceFirst(URL_REGEX, EMPTY_STRING);
    }

    public static String convertUrl(long id) {
        return id == 0 ? Character.toString(HASH_CHARACTERS[0]) : hashUrl(id);
    }

    private static String hashUrl(long id) {
        StringBuilder hash = new StringBuilder();
        do {
            hash.append(HASH_CHARACTERS[(int) (id % BASE)]);
            id /= BASE;
        } while (id > 0);
        return hash.toString();
    }
}

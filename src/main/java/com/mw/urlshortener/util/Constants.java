package com.mw.urlshortener.util;

public final class Constants {
    protected static final int BASE = 62;
    private static final String HASH_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    protected static final char[] HASH_CHARACTERS = HASH_STRING.toCharArray();

    public static final String INDEX = "index";
    public static final String PROCESSED = "processed";
    public static final String DTO = "DTO";
    public static final String URL_LIST = "URL_LIST";

    public static final String TABLE_NAME = "URL";
    public static final String HTTP_PROTOCOL = "http://";
    public static final String DATE_TIME_PATTERN = "dd/MM/YYYY HH:mm";

    public static final String URL_FAILURE = "URL does not exist";
    public static final String SHORT_LINK_EXPIRED = "Your short link expired";
    public static final String REDIRECT_FAILURE = "Unexpected redirect error";
    public static final String EMPTY_STRING = "";

    protected static final String URL_REGEX = "^(http[s]?://www\\.|http[s]?://|www\\.|http[s]?:www\\.|http[s]?//www\\.|http[s]?:|http[s]?//)";

    private Constants() {}
}

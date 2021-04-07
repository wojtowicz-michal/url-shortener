package com.mw.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.mw.urlshortener.util.Constants.DATE_TIME_PATTERN;

@Getter
@Setter
public class RedirectionDto {
    private String url;
    private String alias;

    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    private DateTime expireDate;
}

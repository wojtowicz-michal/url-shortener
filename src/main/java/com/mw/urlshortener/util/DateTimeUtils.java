package com.mw.urlshortener.util;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class DateTimeUtils {
    private static int defaultExpireDays;

    private DateTimeUtils() {}

    public static DateTime getDefaultExpireDateTime() {
        return getCurrentDateTime().plusDays(defaultExpireDays);
    }

    public static DateTime getCurrentDateTime() {
        DateTime now = new DateTime();
        return now.minusSeconds(now.getSecondOfMinute()).minusMillis(now.getMillisOfSecond());
    }

    @Value("${defaultExpireDays}")
    private void setDefaultExpireDays(int defaultExpireDays){
        DateTimeUtils.defaultExpireDays = defaultExpireDays;
    }
}

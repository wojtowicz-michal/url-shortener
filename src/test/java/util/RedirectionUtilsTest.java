package util;

import com.mw.urlshortener.util.RedirectionUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RedirectionUtilsTest {

    @Test
    void removeUrlPrefixSuccessful() {
        String expected = "github.com";
        assertEquals(expected, RedirectionUtils.removeUrlPrefix("https://www.github.com"));
        assertEquals(expected, RedirectionUtils.removeUrlPrefix("https://github.com"));
        assertEquals(expected, RedirectionUtils.removeUrlPrefix("www.github.com"));
    }

    @Test
    void convertUrlSuccessful() {
        assertEquals("a", RedirectionUtils.convertUrl(0));
        assertEquals("Z", RedirectionUtils.convertUrl(51));
        assertEquals("1", RedirectionUtils.convertUrl(53));
    }
}

package service;

import com.mw.urlshortener.dto.RedirectionDto;
import com.mw.urlshortener.entity.RedirectionEntity;
import com.mw.urlshortener.repository.RedirectionRepository;
import com.mw.urlshortener.service.RedirectionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.mw.urlshortener.util.Constants.*;
import static com.mw.urlshortener.util.DateTimeUtils.getCurrentDateTime;
import static com.mw.urlshortener.util.DateTimeUtils.getDefaultExpireDateTime;
import static com.mw.urlshortener.util.RedirectionUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedirectionServiceTest {
    @InjectMocks
    private RedirectionService redirectionService;

    @Mock
    private RedirectionRepository redirectionRepository;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Test
    void shortenUrlSuccessful() {
        //given
        RedirectionDto redirectionDto = new RedirectionDto();
        redirectionDto.setUrl("github.com");

        RedirectionEntity redirectionEntity = new RedirectionEntity();
        redirectionEntity.setId(734859L);

        //when
        when(redirectionRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(redirectionEntity));
        when(redirectionRepository.findByUrlAndExpireDate(anyString(), any())).thenReturn(Optional.empty());
        redirectionService.shortenUrl(redirectionDto);

        //then
        assertEquals(convertUrl(734859), redirectionDto.getAlias());
    }

    @Test
    void doShortenUrlWhenDefaultExpireDate() {
        //given
        RedirectionDto redirectionDto = new RedirectionDto();
        redirectionDto.setUrl("github.com");

        //when
        redirectionService.shortenUrl(redirectionDto);

        //then
        assertEquals(getDefaultExpireDateTime(), redirectionDto.getExpireDate());
    }

    @Test
    void doShortenUrlWhenAliasAlreadyExists() {
        //given
        RedirectionDto redirectionDto = new RedirectionDto();
        redirectionDto.setUrl("github.com");

        RedirectionEntity redirectionEntity = new RedirectionEntity();
        redirectionEntity.setAlias("zxgTwf");

        //when
        when(redirectionRepository.findByUrlAndExpireDate(anyString(), any())).thenReturn(Optional.of(redirectionEntity));
        redirectionService.shortenUrl(redirectionDto);

        //then
        assertEquals("zxgTwf", redirectionDto.getAlias());
    }

    @Test
    void redirectSuccessful() {
        //given
        RedirectionEntity redirectionEntity = new RedirectionEntity();
        redirectionEntity.setExpireDate(getCurrentDateTime().toDate());

        //when
        when(redirectionRepository.findByAlias(anyString())).thenReturn(Optional.of(redirectionEntity));
        String response = redirectionService.redirect(anyString(), httpServletResponse);

        //then
        assertEquals(EMPTY_STRING, response);
    }

    @Test
    void doRedirectWhenAliasNotExists() {
        //given
        RedirectionEntity redirectionEntity = new RedirectionEntity();
        redirectionEntity.setExpireDate(getCurrentDateTime().toDate());

        //when
        when(redirectionRepository.findByAlias(anyString())).thenReturn(Optional.empty());
        String response = redirectionService.redirect(anyString(), httpServletResponse);

        //then
        assertEquals(URL_FAILURE, response);
    }

    @Test
    void doRedirectWhenAliasExpired() {
        //given
        RedirectionEntity redirectionEntity = new RedirectionEntity();
        redirectionEntity.setExpireDate(getCurrentDateTime().minusDays(1).toDate());

        //when
        when(redirectionRepository.findByAlias(anyString())).thenReturn(Optional.of(redirectionEntity));
        String response = redirectionService.redirect(anyString(), httpServletResponse);

        //then
        assertEquals(SHORT_LINK_EXPIRED, response);
    }
}

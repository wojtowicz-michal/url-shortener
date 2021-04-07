package com.mw.urlshortener.service;

import com.mw.urlshortener.dto.RedirectionDto;
import com.mw.urlshortener.entity.RedirectionEntity;
import com.mw.urlshortener.repository.RedirectionRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.mw.urlshortener.util.Constants.*;
import static com.mw.urlshortener.util.DateTimeUtils.getCurrentDateTime;
import static com.mw.urlshortener.util.DateTimeUtils.getDefaultExpireDateTime;
import static com.mw.urlshortener.util.RedirectionUtils.convertUrl;
import static com.mw.urlshortener.util.RedirectionUtils.removeUrlPrefix;

@Service
public class RedirectionService {
    private final RedirectionRepository redirectionRepository;

    @Autowired
    public RedirectionService(RedirectionRepository redirectionRepository) {
        this.redirectionRepository = redirectionRepository;
    }

    public void shortenUrl(RedirectionDto redirectionDto) {
        Optional<RedirectionEntity> optionalRedirectionEntity = redirectionRepository.findTopByOrderByIdDesc();
        long id = optionalRedirectionEntity.map(RedirectionEntity::getId).orElse(0L);

        DateTime expireDate;
        Optional<DateTime> optionalExpireDate = Optional.ofNullable(redirectionDto.getExpireDate());
        if (optionalExpireDate.isEmpty()) {
            expireDate = getDefaultExpireDateTime();
            redirectionDto.setExpireDate(expireDate);
        } else {
            expireDate = optionalExpireDate.get();
        }

        String url = removeUrlPrefix(redirectionDto.getUrl());
        String alias = createAlias(id, url, expireDate.toDate());
        redirectionDto.setAlias(alias);
    }

    private String createAlias(long id, String url, Date expireDate) {
        Optional<RedirectionEntity> optionalRedirectionEntity = redirectionRepository.findByUrlAndExpireDate(url, expireDate);
        if (optionalRedirectionEntity.isPresent()) {
            return optionalRedirectionEntity.get().getAlias();
        }
        String alias = convertUrl(id);
        redirectionRepository.save(new RedirectionEntity(url, alias, expireDate));
        return alias;
    }

    public String redirect(String alias, HttpServletResponse httpServletResponse) {
        Optional<RedirectionEntity> optionalRedirectionEntity = redirectionRepository.findByAlias(alias);
        if (optionalRedirectionEntity.isEmpty()) return URL_FAILURE;

        RedirectionEntity redirectionEntity = optionalRedirectionEntity.get();
        if (isAliasExpired(redirectionEntity)) {
            redirectionRepository.delete(redirectionEntity);
            return SHORT_LINK_EXPIRED;
        }

        try {
            httpServletResponse.sendRedirect(HTTP_PROTOCOL + redirectionEntity.getUrl());
        } catch (IOException e) {
            return REDIRECT_FAILURE;
        }
        return EMPTY_STRING;
    }

    private boolean isAliasExpired(RedirectionEntity redirectionEntity) {
        DateTime expireDate = new DateTime(redirectionEntity.getExpireDate());
        return expireDate.isBefore(getCurrentDateTime());
    }

    public List<RedirectionEntity> getShortenedUrls() {
        return redirectionRepository.findAll();
    }
}

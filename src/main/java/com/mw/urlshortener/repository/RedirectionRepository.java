package com.mw.urlshortener.repository;

import com.mw.urlshortener.entity.RedirectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface RedirectionRepository extends JpaRepository<RedirectionEntity, Long> {
    Optional<RedirectionEntity> findByAlias(String alias);
    Optional<RedirectionEntity> findTopByOrderByIdDesc();
    Optional<RedirectionEntity> findByUrlAndExpireDate(String url, Date expireDate);
}

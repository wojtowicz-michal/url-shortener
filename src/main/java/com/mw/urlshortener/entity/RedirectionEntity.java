package com.mw.urlshortener.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static com.mw.urlshortener.util.Constants.TABLE_NAME;

@Data
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME)
public class RedirectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private String url;

    @Column(unique = true, nullable = false)
    private String alias;

    @Column(nullable = false)
    private Date expireDate;

    public RedirectionEntity(String url, String alias, Date expireDate) {
        this.url = url;
        this.alias = alias;
        this.expireDate = expireDate;
    }
}

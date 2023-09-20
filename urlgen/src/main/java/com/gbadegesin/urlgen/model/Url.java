package com.gbadegesin.urlgen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Table(name = "usa_web_address",
        uniqueConstraints = {@UniqueConstraint(name = "users_url_unique", columnNames = "setUrl"),
                @UniqueConstraint(name = "users_qr_unique", columnNames = "qrCode")})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long urlId;
    private String setUrl;
    private String originalUrl;

    //stored as a base64 string
    private String qrCode;
    private boolean isActive;
    private Long visitCount;
    private boolean isCustom;
    private LocalDateTime creationDate;
    private String userId;

}

package com.gbadegesin.urlgen.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UrlRequest {

    private String desiredUrl;
    private String originalUrl;
    private String usernameOrEmail;
    private Long urlId;
}

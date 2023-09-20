package com.gbadegesin.urlgen.service.clients;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("qrgen")
public interface QRGenFeignClient {

    @PostMapping(value = "qr/generate",  consumes = MediaType.TEXT_PLAIN_VALUE,
            produces= MediaType.TEXT_PLAIN_VALUE)
    String generateQR(@RequestBody String urlEncRequest);
}

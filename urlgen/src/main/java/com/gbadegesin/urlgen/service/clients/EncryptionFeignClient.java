package com.gbadegesin.urlgen.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("encryption")
public interface EncryptionFeignClient {

    @PostMapping(value = "/encryption/encrypt", consumes = MediaType.TEXT_PLAIN_VALUE,
            produces= MediaType.TEXT_PLAIN_VALUE)
    <T> String encryptPayload(@RequestBody T payload);

    @PostMapping(value = "/encryption/decrypt", consumes = MediaType.TEXT_PLAIN_VALUE,
            produces= MediaType.TEXT_PLAIN_VALUE)
    String decryptPayload(@RequestBody String request);
}

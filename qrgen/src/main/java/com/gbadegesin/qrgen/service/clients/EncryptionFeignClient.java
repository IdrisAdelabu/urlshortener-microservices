package com.gbadegesin.qrgen.service.clients;

import com.gbadegesin.qrgen.model.DecryptionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("encryption")
public interface EncryptionFeignClient {

    @PostMapping(value = "/encrypt", consumes = MediaType.TEXT_PLAIN_VALUE,
            produces= MediaType.TEXT_PLAIN_VALUE)
    <T> String encryptPayload(@RequestBody T payload);

    @PostMapping(value = "/decrypt", consumes = MediaType.TEXT_PLAIN_VALUE,
            produces= MediaType.TEXT_PLAIN_VALUE)
    String decryptPayload(@RequestBody String request);
}

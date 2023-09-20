package com.gbadegesin.encryption.controller;

import com.gbadegesin.encryption.encryption.JSONUtils;
import com.gbadegesin.encryption.model.DecryptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/encryption")
@RequiredArgsConstructor
public class EncryptionController {

    private final JSONUtils jsonUtils;

    @PostMapping(value = "/encryption/encrypt", consumes = MediaType.TEXT_PLAIN_VALUE,
            produces= MediaType.TEXT_PLAIN_VALUE)
    public <T> String encryptPayload(@RequestBody T payload) {
        return jsonUtils.encryptObjectToString(payload);
    }

    @PostMapping(value = "/encryption/decrypt", consumes = MediaType.TEXT_PLAIN_VALUE,
            produces= MediaType.TEXT_PLAIN_VALUE)
    public String decryptPayload(@RequestBody String request) {
        return jsonUtils.decryptStringToJson(request);
    }
}

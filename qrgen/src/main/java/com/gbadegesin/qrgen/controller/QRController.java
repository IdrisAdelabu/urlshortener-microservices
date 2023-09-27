package com.gbadegesin.qrgen.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gbadegesin.qrgen.exception.GenericException;
import com.gbadegesin.qrgen.model.DecryptionRequest;
import com.gbadegesin.qrgen.service.clients.EncryptionFeignClient;
import com.gbadegesin.qrgen.service.QRService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/qr")
@RequiredArgsConstructor
@Slf4j
public class QRController {

    private final static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    private final EncryptionFeignClient encryptionFeignClient;
    private final QRService qrService;
    //todo generate qr code for url

    @PostMapping(value = "/generate",  consumes = MediaType.TEXT_PLAIN_VALUE,
            produces= MediaType.TEXT_PLAIN_VALUE)
    public String generateQR(@RequestBody String urlEncRequest) {

        String url = encryptionFeignClient.decryptPayload(urlEncRequest);
        String qrCode = qrService.generateQRCode(url);
        return encryptionFeignClient.encryptPayload(qrCode);
    }

//    public static <T> T toObject(String json, Class<T> clazz) {
//
//        try {
//            return objectMapper.readValue(json, clazz);
//        }catch (Exception e){
//            log.error(e.getMessage(), e);
//            throw new GenericException(e.getLocalizedMessage(), "Couldn't string to object payload", HttpStatus.BAD_REQUEST);
//        }
//    }
}

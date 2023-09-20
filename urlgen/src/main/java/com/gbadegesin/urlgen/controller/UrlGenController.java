package com.gbadegesin.urlgen.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gbadegesin.urlgen.dto.request.UrlRequest;
import com.gbadegesin.urlgen.dto.response.Response;
import com.gbadegesin.urlgen.exception.GenericException;
import com.gbadegesin.urlgen.service.clients.EncryptionFeignClient;
import com.gbadegesin.urlgen.service.interfaces.UrlGenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/url")
@RequiredArgsConstructor
@Slf4j
public class UrlGenController {

    private final static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    private final UrlGenService service;
    private final EncryptionFeignClient encryptionFeignClient;

    @PostMapping(value = "/generate",  consumes = MediaType.TEXT_PLAIN_VALUE,
            produces= MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> login(@RequestBody String request, HttpServletRequest httpServletRequest) {

        String jsonRequest = encryptionFeignClient.decryptPayload(request);
        UrlRequest urlRequest = toObject(jsonRequest, UrlRequest.class);
        Response resp;

        if (urlRequest.getDesiredUrl() == null) {
            resp = service.genericUrl(urlRequest, httpServletRequest);
        } else {
            resp = service.customUrl(urlRequest, httpServletRequest);
        }
        return ResponseEntity.ok().body(encryptionFeignClient.encryptPayload(resp));
    }


    //---


    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> login(@RequestBody UrlRequest request, HttpServletRequest httpServletRequest) {

        if (request.getDesiredUrl() == null) {
            return ResponseEntity.ok().body(service.genericUrl(request, httpServletRequest));
        } else {
            return ResponseEntity.ok().body(service.customUrl(request, httpServletRequest));
        }

    }

    @GetMapping(value = "/getServices", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getRegisteredServices() {
        return ResponseEntity.ok().body(service.getServices());
    }

    @GetMapping(value = "/getServiceDetails", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getServiceDetails() {
        return ResponseEntity.ok().body(service.getServiceDetails());
    }

    private static <T> T toObject(String json, Class<T> clazz) {

        try {
            return objectMapper.readValue(json, clazz);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GenericException(e.getLocalizedMessage(), "Couldn't string to object payload", HttpStatus.BAD_REQUEST);
        }
    }
}

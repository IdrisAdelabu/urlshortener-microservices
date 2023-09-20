package com.gbadegesin.urlgen.service.interfaces;


import com.gbadegesin.urlgen.dto.request.UrlRequest;
import com.gbadegesin.urlgen.dto.response.Response;
import jakarta.servlet.http.HttpServletRequest;

public interface UrlGenService {

    Response genericUrl(UrlRequest request, HttpServletRequest httpServletRequest);
    Response customUrl(UrlRequest request, HttpServletRequest httpServletRequest);

    Response getServices();

    Response getServiceDetails();
}

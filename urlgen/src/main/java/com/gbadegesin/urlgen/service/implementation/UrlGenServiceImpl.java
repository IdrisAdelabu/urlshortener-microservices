package com.gbadegesin.urlgen.service.implementation;


import com.gbadegesin.urlgen.dto.request.UrlRequest;
import com.gbadegesin.urlgen.dto.response.Response;
import com.gbadegesin.urlgen.exception.GenericException;
import com.gbadegesin.urlgen.model.Url;
import com.gbadegesin.urlgen.repository.UrlGenRepository;
import com.gbadegesin.urlgen.service.clients.QRGenFeignClient;
import com.gbadegesin.urlgen.service.interfaces.UrlGenService;
import com.gbadegesin.urlgen.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlGenServiceImpl implements UrlGenService {

    private final UrlGenRepository repository;
//    private final UserRepository userRepository;
    private final QRGenFeignClient qrCodeGenerator;
    private final DiscoveryClient discoveryClient;

    @Override
    public Response genericUrl(UrlRequest request, HttpServletRequest httpServletRequest) {

        String usernameOrEmail = request.getUsernameOrEmail();

        //todo check if user records exists or any other form of user authentication
        String user = "user";

//        User user = userRepository.findUserByEmailOrUsername((usernameOrEmail.contains("@") ? usernameOrEmail : null),
//                (usernameOrEmail.contains("@") ? null : usernameOrEmail)).orElse(null);

        if (user != null) {
            Url url = new Url();
            url
                    .setOriginalUrl(request.getOriginalUrl())
                    .setSetUrl(generateGenericUrl())
                    .setCustom(false)
                    .setActive(true)
                    .setCreationDate(LocalDateTime.now())
                    .setQrCode(generateQRCode(url.getSetUrl()))
                    .setUserId(usernameOrEmail);

            return setUrl(url);
        } else {
            return new Response().setResponseCode("90").setResponseMessage("User does not exist");
        }
    }

    @Override
    public Response customUrl(UrlRequest request, HttpServletRequest httpServletRequest) {

        String usernameOrEmail = request.getUsernameOrEmail();

        //todo check if user records exists or any other form of user authentication
        String user = "user";
//        User user = userRepository.findUserByEmailOrUsername((usernameOrEmail.contains("@") ? usernameOrEmail : null),
//                (usernameOrEmail.contains("@") ? null : usernameOrEmail)).orElse(null);

        if (user != null) {
            Url url = new Url();
            url
                    .setOriginalUrl(request.getOriginalUrl())
                    .setSetUrl(request.getDesiredUrl())
                    .setCustom(true)
                    .setActive(true)
                    .setCreationDate(LocalDateTime.now())
                    .setQrCode(generateQRCode(url.getSetUrl()))
                    .setUserId(usernameOrEmail);

            return setUrl(url);
        } else {
            return new Response().setResponseCode("90").setResponseMessage("User does not exist");
        }
    }

    @Override
    public Response getServices() {

        List<String> serviceNames = discoveryClient.getServices();
        return new Response().setData(serviceNames).setResponseCode("00").setResponseMessage("SUCCESSFUL");
    }

    @Override
    public Response getServiceDetails() {
        List<String> serviceIds = (List<String>) getServices().getData();

        Map<String, List<ServiceInstance>> serviceInstances = new HashMap<>();

        for (String service:serviceIds) {
            serviceInstances.put(service,discoveryClient.getInstances(service));
        }

        return new Response().setData(serviceInstances).setResponseCode("00").setResponseMessage("SUCCESSFUL");

    }


    private Response setUrl(Url url) {
        try {
            Url savedUrl = repository.save(url);
            return (new Response()).setData(url).setResponseMessage("SUCCESSFUL").setResponseCode("00").setData(savedUrl);
        } catch (Exception ex) {
            throw new GenericException(ex.getLocalizedMessage(), INTERNAL_SERVER_ERROR);
        }
    }

    private String generateGenericUrl() {
        return StringUtils.generateUrl();
    }
    private String generateQRCode(String url) {
        return qrCodeGenerator.generateQR(url);
    }
}

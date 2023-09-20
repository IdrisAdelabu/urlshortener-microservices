package com.gbadegesin.urlgen.service.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user")
public interface UserFeignClient {
}

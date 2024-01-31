package com.bank.transfersystem.proxy;

import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "mock-authorization", url = "https://run.mocky.io")
public interface AuthorizingServiceProxy {

    @RequestLine("GET /v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
    @Headers("Content-Type: application/json")
    String getAuthorization();

    @RequestLine("GET /v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
    @Headers("Content-Type: application/json")
    String getMailServiceStatus();
}

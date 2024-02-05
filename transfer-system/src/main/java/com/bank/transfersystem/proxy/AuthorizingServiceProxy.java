package com.bank.transfersystem.proxy;

import com.bank.transfersystem.dto.GetMockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "mock-authorization", url = "https://run.mocky.io")
public interface AuthorizingServiceProxy {
    
    @GetMapping("/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
    GetMockDTO getAuthorization();

    @GetMapping("/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
    GetMockDTO getMailServiceStatus();
}

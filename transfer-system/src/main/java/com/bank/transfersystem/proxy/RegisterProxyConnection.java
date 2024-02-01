package com.bank.transfersystem.proxy;

import com.bank.transfersystem.dto.ProxyResponseDTO;
import com.bank.transfersystem.dto.WalletDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "register-system", url = "localhost:8000")
public interface RegisterProxyConnection {
    @GetMapping("/user/{id}")
    ProxyResponseDTO getUserById(@PathVariable(name = "id") Long id);

    @GetMapping("/user/wallet/{id}")
    WalletDTO getWalletById(@PathVariable(name = "id") Long id);
}

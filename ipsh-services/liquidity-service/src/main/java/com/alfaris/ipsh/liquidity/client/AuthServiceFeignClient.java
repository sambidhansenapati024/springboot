package com.alfaris.ipsh.liquidity.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.entity.PshCasHry;


@FeignClient(name = "auth-service",url="${client.auth-service.url}")
public interface AuthServiceFeignClient {

	@PostMapping(value = "/auth-service/user/audit")
	ServiceResponse createAuditLog(PshCasHry audit);

}

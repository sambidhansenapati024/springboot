package com.alfaris.ipsh.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.alfaris.ipsh.security.dto.ServiceResponse;
import com.alfaris.ipsh.security.entity.PshCasHry;

@FeignClient(name = "auth-service", url = "${client.auth-service.url}")
public interface AuthServiceFeignClient {

	@PostMapping(value = "/auth-service/user/audit")
	ServiceResponse createAuditLog(PshCasHry audit);

}

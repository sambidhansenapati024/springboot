package com.alfaris.ipsh.subscription.client;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alfaris.ipsh.subscription.entity.GroupScreen;
import com.alfaris.ipsh.subscription.exception.RecordNotFoundException;

@FeignClient(name = "security-service",url="${client.security-service.url}")
public interface SecurityServiceFeignClient {

	@GetMapping("/security-service/security/securityPermission/notifyGroup")
	List<String> notifyGroup(@RequestParam("permissionType") String permissionType,
			@RequestParam("screenId") String screenId);

	@GetMapping("/security-service/security/securityUser/bankId")
	String getBankId(@RequestParam("userId") String userId);

	@GetMapping("/security-service/security/securityPermission/getPermission")
	public ResponseEntity<GroupScreen> getPermission(@RequestParam("screenId") String screenId)
			throws RecordNotFoundException, ParseException;
}
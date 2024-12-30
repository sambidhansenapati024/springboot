package com.alfaris.ipsh.security.client;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alfaris.ipsh.security.dto.GroupScreen;
import com.alfaris.ipsh.security.exception.RecordNotFoundException;

@FeignClient(name = "security-service", url = "${client.security-service.url}")
public interface SecurityServiceFeignClient {

	@GetMapping("/security-service/security/securityPermission/notifyGroup")
	List<String> notifyGroup(@RequestParam("permissionType") String permissionType,
			@RequestParam("screenId") String screenId);

	@GetMapping("/security-service/security/securityPermission/getPermission")
	public ResponseEntity<GroupScreen> getPermission(@RequestParam("screenId") String screenId)
			throws RecordNotFoundException, ParseException;
}
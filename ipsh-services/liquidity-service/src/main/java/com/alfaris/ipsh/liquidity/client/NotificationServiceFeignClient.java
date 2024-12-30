package com.alfaris.ipsh.liquidity.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.entity.PshActHry;
import com.alfaris.ipsh.liquidity.entity.PshUsrNty;

@FeignClient(name = "notification-service",url="${client.notification-service.url}")
public interface NotificationServiceFeignClient {

	@PostMapping("/notification-service/notification")
	ServiceResponse save(PshUsrNty notification);

	@PutMapping("/notification-service/notification")
	ServiceResponse update(PshUsrNty notification);

	@GetMapping("/notification-service/notification/{notificationId}")
	PshUsrNty getById(@PathVariable("notificationId") String notificationId);
	
	@PostMapping("/notification-service/activity/saveActivity")
	ServiceResponse saveActivity(PshActHry activity);
}
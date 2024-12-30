package com.alfaris.ipsh.subscription;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.alfaris.ipsh.subscription.client.SecurityServiceFeignClient;
import com.alfaris.ipsh.subscription.entity.GroupScreen;


@Component("AC")
public class AuthorizationComponent implements IAuthorizationComponent {

	@Autowired
	SecurityServiceFeignClient securityServiceFeignClient;
	
	@Override
	public boolean hasAccess(String screenId, String accessType) {
	

		try {
			ResponseEntity<GroupScreen> permission = securityServiceFeignClient.getPermission(screenId);
			GroupScreen gr = permission.getBody();
			if ("R".equals(accessType) && gr.getDspFl().equals("Y")) {
		
				return true;
			} else if ("C".equals(accessType) && gr.getAddFl().equals("Y")) {
				return true;
			} else if ("U".equals(accessType) && gr.getUpdFl().equals("Y")) {
				return true;
			} else if ("D".equals(accessType) && gr.getDelFl().equals("Y")) {
				return true;
			} else if ("V".equals(accessType) && gr.getVerFl().equals("Y")) {
				return true;
			}
//
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
		
	}

	public boolean getRandomBoolean() {
		Random random = new Random();
		return random.nextBoolean();
	}
}

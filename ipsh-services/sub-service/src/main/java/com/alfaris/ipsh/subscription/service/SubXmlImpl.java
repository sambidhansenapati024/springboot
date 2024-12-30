package com.alfaris.ipsh.subscription.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


import com.alfaris.ipsh.subscription.dto.SubscriptionDto;
import com.alfaris.ipsh.subscription.entity.Subscription;
import com.alfaris.ipsh.subscription.entity.SubscriptionPk;
import com.alfaris.ipsh.subscription.repo.SubscRepo;

import com.alfaris.ipsh.subscription.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import soap.subscription.ipsh.alfaris.com.AddSubscriptionRequest;
import soap.subscription.ipsh.alfaris.com.AddSubscriptionResponse;
import soap.subscription.ipsh.alfaris.com.ServiceResponse;
import soap.subscription.ipsh.alfaris.com.UpdateSubscriptionRequest;
import soap.subscription.ipsh.alfaris.com.UpdateSubscriptionResponse;
@Service
@RequiredArgsConstructor
@Slf4j
public class SubXmlImpl implements SubXml {
	
private final SubscRepo repo;
	@Override
	public AddSubscriptionResponse add(AddSubscriptionRequest request) {
		SubscriptionDto dto=new SubscriptionDto();
		BeanUtils.copyProperties(request.getSubscriptionDto(), dto);
		SubscriptionPk pk=SubscriptionPk.builder()
				.uniqSubId(generateAlphanumericId(5))
				.platforms(request.getSubscriptionDto().getPlatforms())
				.uniqUserId((long) ThreadLocalRandom.current().nextInt(100, 10000))
				.userName(request.getSubscriptionDto().getUserName())
				.build();
		Optional<Subscription> getDetails=repo.findById(pk);
		if(getDetails.isEmpty()) {
			
		}
		Subscription subscription = Subscription.builder().pk(pk).comments(Constants.KYC_CMMT.CREATE_COMNT)
				.status(Constants.MESSAGE_STATUS.PROCESSED).email(dto.getEmail())
				.fullName(dto.getFullName()).paymentMethod(dto.getPaymentMethod())
				.createdBy(getUserName()).modifiedBy(getUserName())
				.verifiedBy(dto.getVerifiedBy()).paymentBy(dto.getUserName())
				.duration(dto.getDuration()).price(dto.getPrice())
				.fPrice(dto.getDuration() * dto.getPrice()).creationTime(LocalDateTime.now())
				.modificationTime(LocalDateTime.now())
				.verificationTime(dto.getVerificationTime()).subscriptionDate(LocalDate.now())
				.patmentDate(LocalDateTime.now()).mobileNubmer(dto.getMobileNumber())
				.endSubscriptionDate(dto.getEndSubscriptionDate()).build();
				try {
					repo.save(subscription);
					return response("Sucess", "Created Succesfully", List.of());
			
		} catch (Exception e) {
			
			log.error("error occuted at {}", e.getMessage());
		}
				return response("Sucess", "Created Succesfully", List.of());
		
		
	}

	@Override
	public UpdateSubscriptionResponse update(UpdateSubscriptionRequest request) {
		
		return null;
	}
	private String generateAlphanumericId(int length) { 
		String alphanumericCharacters = Constants.ALF_NUM_VALUE;
		StringBuilder id = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(alphanumericCharacters.length());
			id.append(alphanumericCharacters.charAt(randomIndex));
		}

		return id.toString();
	}
	
	private String getUserName() {
		Jwt p = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> claims = p.getClaims();
		return (String) claims.get("preferred_username");

	}
	
	private AddSubscriptionResponse response(String status, String messageCode, List<JSONObject> details) {
		ServiceResponse serviceResponse = new ServiceResponse();
		serviceResponse.setCode(status);
		serviceResponse.setMessage(messageCode);
				
		AddSubscriptionResponse response = new AddSubscriptionResponse();
		response.setServiceResponse(serviceResponse);
		return response;
	}


}

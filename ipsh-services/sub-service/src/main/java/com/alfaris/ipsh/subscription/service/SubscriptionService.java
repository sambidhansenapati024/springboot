package com.alfaris.ipsh.subscription.service;

import java.security.Principal;
import java.util.List;

import org.json.simple.JSONObject;

import com.alfaris.ipsh.subscription.dto.ServiceResponse;
import com.alfaris.ipsh.subscription.dto.ServiceResponses;
import com.alfaris.ipsh.subscription.dto.SubscriptionDto;
import com.alfaris.ipsh.subscription.entity.Platforms;
import com.alfaris.ipsh.subscription.exception.NoRecordPresentException;
import com.alfaris.ipsh.subscription.exception.RecordNotFoundException;
import com.alfaris.ipsh.subscription.exception.SubscriptionAlreadyExistsException;


public interface SubscriptionService {
	ServiceResponse addSubscription(SubscriptionDto subscriptionDto,Principal principal) throws SubscriptionAlreadyExistsException;
	ServiceResponse updateSubscription(SubscriptionDto subscriptionDto,Principal principal) throws NoRecordPresentException;
	ServiceResponse deleteSubscription(SubscriptionDto subscriptionDto,Principal principal) throws NoRecordPresentException;
	ServiceResponse verifySubscription(String uniqSubId,Long uniqUserId,String platforms,String userName,Principal principal) throws RecordNotFoundException;
	ServiceResponses getSubById(String uniqSubId,Long uniqUserId,String platforms,String userName,Principal principal) throws RecordNotFoundException;
	JSONObject searchByLimit(String searchParam, int parseInt, int parseInt2);
	List<Platforms> getAllPlatforms();


}

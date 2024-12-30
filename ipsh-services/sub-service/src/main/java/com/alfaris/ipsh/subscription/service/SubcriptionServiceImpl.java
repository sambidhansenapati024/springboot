package com.alfaris.ipsh.subscription.service;

import java.lang.reflect.Field;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import com.alfaris.ipsh.subscription.dto.ServiceResponse;
import com.alfaris.ipsh.subscription.dto.ServiceResponses;
import com.alfaris.ipsh.subscription.dto.SubscriptionDto;
import com.alfaris.ipsh.subscription.entity.Platforms;
import com.alfaris.ipsh.subscription.entity.Subscription;
import com.alfaris.ipsh.subscription.entity.SubscriptionPk;
import com.alfaris.ipsh.subscription.exception.NoRecordPresentException;
import com.alfaris.ipsh.subscription.exception.RecordNotFoundException;
import com.alfaris.ipsh.subscription.exception.SubscriptionAlreadyExistsException;
import com.alfaris.ipsh.subscription.repo.PlatformRepo;
import com.alfaris.ipsh.subscription.repo.SubscRepo;
import com.alfaris.ipsh.subscription.repo.spec.SubscMangSpec;
import com.alfaris.ipsh.subscription.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubcriptionServiceImpl implements SubscriptionService {

	private final SubscRepo subscRepo;
	private final MessageSource messageSource;
	private final PlatformRepo platformRepo;
	private final ActionServiceImpl actionServiceImpl;
	@Override
	public ServiceResponse addSubscription(SubscriptionDto subscriptionDto, Principal principal)throws SubscriptionAlreadyExistsException {
		 
		SubscriptionPk pk = setPK(subscriptionDto);
		Optional<Subscription> getDetails = subscRepo.findById(pk);
		if (getDetails.isPresent()) {
			throw new SubscriptionAlreadyExistsException();
		}
		Subscription subscription = Subscription.builder().pk(pk).comments(Constants.KYC_CMMT.CREATE_COMNT)
				.status(Constants.MESSAGE_STATUS.PROCESSED).email(subscriptionDto.getEmail())
				.fullName(subscriptionDto.getFullName()).paymentMethod(subscriptionDto.getPaymentMethod())
				.createdBy(getUserName()).modifiedBy(getUserName())
				.verifiedBy(subscriptionDto.getVerifiedBy()).paymentBy(subscriptionDto.getUserName())
				.duration(subscriptionDto.getDuration()).price(subscriptionDto.getPrice())
				.fPrice(subscriptionDto.getDuration() * subscriptionDto.getPrice()).creationTime(LocalDateTime.now())
				.modificationTime(LocalDateTime.now())
				.verificationTime(subscriptionDto.getVerificationTime()).subscriptionDate(LocalDate.now())
				.patmentDate(LocalDateTime.now()).mobileNubmer(subscriptionDto.getMobileNumber())
				.endSubscriptionDate(subscriptionDto.getEndSubscriptionDate()).build();
		try {
			subscRepo.save(subscription);
			actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.CS);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0019", null, LocaleContextHolder.getLocale()), null);
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.CF);
			log.error("error occuted at {}", e.getMessage());
		}
		return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
				messageSource.getMessage("user.VAL0020", null, LocaleContextHolder.getLocale()), null);
	}
	@Override
	public ServiceResponse updateSubscription(SubscriptionDto subscriptionDto, Principal principal)
			throws NoRecordPresentException {
		SubscriptionPk pk = setPKToUpdate(subscriptionDto);
		Optional<Subscription> getDetails = subscRepo.findById(pk);
		Subscription existingDetails = getDetails.get();
		boolean isPriceChanged=existingDetails.getDuration() != subscriptionDto.getDuration()|| existingDetails.getPrice() != subscriptionDto.getPrice();
		
		if (getDetails.isEmpty()) {
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.UF);
			throw new NoRecordPresentException();
		} else if (existingDetails.getStatus().equals(Constants.MESSAGE_STATUS.DELETED)) {
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.UF);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("user.VAL0033", null, LocaleContextHolder.getLocale()), null);

		}
		Subscription subscription = Subscription.builder().pk(pk).comments(Constants.KYC_CMMT.UPDATE_COMNT)
				.status(Constants.MESSAGE_STATUS.PROCESSED).email(subscriptionDto.getEmail())
				.fullName(subscriptionDto.getFullName()).paymentMethod(subscriptionDto.getPaymentMethod())
				.createdBy(subscriptionDto.getCreatedBy()).modifiedBy(getUserName())
				.verifiedBy(subscriptionDto.getVerifiedBy()).paymentBy(isPriceChanged?getUserName():subscriptionDto.getPaymentBy())
				.duration(subscriptionDto.getDuration()).price(subscriptionDto.getPrice())
				.fPrice(isPriceChanged?subscriptionDto.getDuration() * subscriptionDto.getPrice():subscriptionDto.getFPrice())
				.creationTime(subscriptionDto.getCreationTime()).modificationTime(LocalDateTime.now())
				.verificationTime(subscriptionDto.getVerificationTime())
				.subscriptionDate(subscriptionDto.getSubscriptionDate()).patmentDate(isPriceChanged?LocalDateTime.now():subscriptionDto.getPatmentDate())
				.mobileNubmer(subscriptionDto.getMobileNumber())
				.endSubscriptionDate(subscriptionDto.getEndSubscriptionDate()).build();
		try {
			subscRepo.save(subscription);
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.US);

			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0021", null, LocaleContextHolder.getLocale()), null);

		} catch (Exception e) {
			log.error("error at {}",e.getMessage());
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.UF);
			System.out.println(e.getMessage());
		}
		return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
				messageSource.getMessage("user.VAL0021", null, LocaleContextHolder.getLocale()), null);

	}

	@Override
	public ServiceResponse deleteSubscription(SubscriptionDto subscriptionDto, Principal principal)
			throws NoRecordPresentException {
		SubscriptionPk pk = setPKToUpdate(subscriptionDto);
		Optional<Subscription> getDetails = subscRepo.findById(pk);
		Subscription existingDetails = getDetails.get();
		if (getDetails.isEmpty()) {
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.DF);
			throw new NoRecordPresentException();
		}

		else if (existingDetails.getStatus().equals(Constants.MESSAGE_STATUS.DELETED)) {
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.DF);

			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("user.VAL0033", null, LocaleContextHolder.getLocale()), null);
		}
		existingDetails.setStatus(Constants.MESSAGE_STATUS.DELETE);
		existingDetails.setModifiedBy(getUserName());
		existingDetails.setModificationTime(LocalDateTime.now());
		existingDetails.setComments(Constants.KYC_CMMT.DELETE_COMNT);
		try {
			subscRepo.save(existingDetails);
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.DS);

			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0029", null, LocaleContextHolder.getLocale()), null);

		} catch (Exception e) {
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.DF);

			// TODO: handle exception
		}
		return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
				messageSource.getMessage("user.VAL0030", null, LocaleContextHolder.getLocale()), null);
	}

	@Override
	public ServiceResponse verifySubscription(String uniqSubId, Long uniqUserId, String platforms, String userName,Principal principal) throws RecordNotFoundException {

		SubscriptionPk pk = setPkWithId(uniqSubId, uniqUserId, platforms, userName, principal);

		Optional<Subscription> getDetails = subscRepo.findById(pk);
		if (getDetails.isEmpty()) {
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.VF);
			throw new RecordNotFoundException();
		} 
		try {
			Subscription existingSubscRequest = getDetails.get();
			String status = existingSubscRequest.getStatus();
			

			switch (status) { 
			    case Constants.MESSAGE_STATUS.VERIFIED,Constants.MESSAGE_STATUS.DELETED:
			        //actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.VF);
			        return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,messageSource.getMessage("user.VAL0028", null, LocaleContextHolder.getLocale()),null);

			    case Constants.MESSAGE_STATUS.DELETE:
			        existingSubscRequest.setStatus(Constants.MESSAGE_STATUS.DELETED);
			        existingSubscRequest.setVerifiedBy(userName);
			        existingSubscRequest.setVerificationTime(LocalDateTime.now());
			        existingSubscRequest.setComments(Constants.KYC_CMMT.DELETE_VERIFY_COMNT);
			        subscRepo.save(existingSubscRequest);
			       // actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.VS);
			        return new ServiceResponse( Constants.MESSAGE_STATUS.SUCCESS,messageSource.getMessage("user.VAL0031", null, LocaleContextHolder.getLocale()), null );

			    default:
			        if (existingSubscRequest.getModifiedBy().equalsIgnoreCase(getUserName())) {
			           // actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.VF);
			            return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,messageSource.getMessage("user.VAL0027", null, LocaleContextHolder.getLocale()), null ); 
			        }
			        break;
			}

			existingSubscRequest.setStatus(Constants.MESSAGE_STATUS.APPROVED);
			existingSubscRequest.setVerifiedBy(getUserName());
			existingSubscRequest.setVerificationTime(LocalDateTime.now());
			existingSubscRequest.setComments(Constants.KYC_CMMT.CREATE_VERIFY_COMNT);

			subscRepo.save(existingSubscRequest);
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.VS);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0025", null, LocaleContextHolder.getLocale()), null);

		} catch (NoSuchMessageException e) {
			//actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.VF);
			System.out.println(e.getMessage());
		}

		return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
				messageSource.getMessage("user.VAL0032", null, LocaleContextHolder.getLocale()), null);

	}

	@Override
	public ServiceResponses getSubById(String uniqSubId, Long uniqUserId, String platforms, String userName,Principal principal) throws RecordNotFoundException {
		
		SubscriptionPk pk = setPkWithId(uniqSubId, uniqUserId, platforms, userName, principal);
		Optional<Subscription> getDetails = subscRepo.findById(pk);
		if (getDetails.isEmpty()) {
			throw new RecordNotFoundException();
		}
		try {
			Subscription subscription = getDetails.get();
			SubscriptionDto subscriptionDto = SubscriptionDto.builder().uniqSubId(uniqSubId).uniqUserId(uniqUserId)
					.userName(userName).platforms(platforms).comments(subscription.getComments())
					.status(subscription.getStatus()).email(subscription.getEmail())
					.fullName(subscription.getFullName()).paymentMethod(subscription.getPaymentMethod())
					.createdBy(subscription.getCreatedBy()).modifiedBy(getUserName())
					.verifiedBy(subscription.getVerifiedBy()).paymentBy(subscription.getPaymentBy())
					.duration(subscription.getDuration()).price(subscription.getPrice())
					.fPrice(subscription.getFPrice()).creationTime(subscription.getCreationTime())
					.modificationTime(subscription.getModificationTime()).verificationTime(subscription.getVerificationTime())
					.subscriptionDate(subscription.getSubscriptionDate()).patmentDate(subscription.getPatmentDate())
					.endSubscriptionDate(subscription.getEndSubscriptionDate())
					.mobileNumber(subscription.getMobileNubmer()).build();
			return new ServiceResponses(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0029", null, LocaleContextHolder.getLocale()),
					List.of(subscriptionDto));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ServiceResponses(Constants.MESSAGE_STATUS.FAILED,
				messageSource.getMessage("user.VAL0029", null, LocaleContextHolder.getLocale()), List.of());

	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject searchByLimit(String searchParam, int start, int pageSize) {
	    JSONObject result = new JSONObject();
	    try {
	        Pageable pageable = createPageable(start, pageSize);
	        Specification<Subscription> spec = SubscMangSpec.getUserSpec(searchParam);

	        Page<Subscription> usersSubscList = fetchSubscriptions(spec, pageable);
	        JSONArray subscriptionArray = convertSubscriptionsToJSONArray(usersSubscList);
	        JSONArray countByStatus = countByStatus(spec);

	        populateResult(result, subscriptionArray, spec, countByStatus);

	    } catch (Exception e) {
	        handleException(e);
	    }
	    return result;
	}

	private Pageable createPageable(int start, int pageSize) {
	    return PageRequest.of(start / pageSize, pageSize);
	}

	private Page<Subscription> fetchSubscriptions(Specification<Subscription> spec, Pageable pageable) {
	    return subscRepo.findAll(spec, pageable);
	}

	@SuppressWarnings("unchecked")
	private JSONArray convertSubscriptionsToJSONArray(Page<Subscription> usersSubscList) {
	    JSONArray array = new JSONArray();
	    for (Subscription userSubsc : usersSubscList) {
	        array.add(convertSubscriptionToJSON(userSubsc));
	    }
	    return array;
	}

	@SuppressWarnings("unchecked")
	private JSONObject convertSubscriptionToJSON(Subscription userSubsc) {
	    JSONObject obj = new JSONObject();
	    try {
	        // Add fields of Subscription
	        Field[] fields = Subscription.class.getDeclaredFields();
	        for (Field field : fields) {
	            field.setAccessible(true);
	            obj.put(field.getName(), field.get(userSubsc));
	        }

	        // Flatten pk object
	        if (userSubsc.getPk() != null) {
	            SubscriptionPk pk = userSubsc.getPk();
	            Field[] pkFields = SubscriptionPk.class.getDeclaredFields();
	            for (Field pkField : pkFields) {
	                pkField.setAccessible(true);
	                obj.put(pkField.getName(), pkField.get(pk));
	            }
	        }

	        // Optionally remove the `pk` field if not needed
	        obj.remove("pk");

	    } catch (IllegalAccessException e) {
	        e.printStackTrace();
	    }
	    return obj;
	}


	@SuppressWarnings("unchecked")
	private void populateResult(JSONObject result, JSONArray subscriptionArray, Specification<Subscription> spec, JSONArray countByStatus) {
	    result.put("aaData", subscriptionArray);
	    result.put("iTotalDisplayRecords", subscRepo.findAll(spec).size());
	    result.put("iTotalRecords", subscRepo.findAll(spec).size());
	    result.put("countByStatus", countByStatus);
	}

	private void handleException(Exception e) {
	    System.out.println("Error: " + e.getMessage());
	    // Uncomment if user logging for exceptions is required
	    // actionServiceImpl.saveUserLog(getUserName(), Constants.ACTION_DTLS.SS);
	}

	@SuppressWarnings("unchecked")
	private JSONArray countByStatus(Specification<Subscription> spec) {
	    JSONArray array = new JSONArray();
	    try {
	        List<Subscription> headerList = subscRepo.findAll(spec);
	        Map<String, Long> countByStatus = headerList.stream()
	                .collect(Collectors.groupingBy(Subscription::getStatus, Collectors.counting()));
	        for (String status : countByStatus.keySet()) {
	            JSONObject obj = new JSONObject();
	            obj.put("name", status);
	            obj.put("count", countByStatus.get(status));
	            array.add(obj);
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Handle any exceptions if needed
	    }
	    return array;
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

	private SubscriptionPk setPK(SubscriptionDto subscriptionDto) {
		SubscriptionPk pk = SubscriptionPk.builder().uniqSubId(generateAlphanumericId(5))
				.uniqUserId((long) ThreadLocalRandom.current().nextInt(100, 10000))
				.userName(subscriptionDto.getUserName()).platforms(subscriptionDto.getPlatforms()).build();

		return pk;
	}

	private SubscriptionPk setPKToUpdate(SubscriptionDto subscriptionDto) {
		SubscriptionPk pk = SubscriptionPk.builder().uniqSubId(subscriptionDto.getUniqSubId())
				.uniqUserId(subscriptionDto.getUniqUserId()).userName(subscriptionDto.getUserName())
				.platforms(subscriptionDto.getPlatforms()).build();

		return pk;
	}

	@Override
	public List<Platforms> getAllPlatforms() {
		return platformRepo.findAll();
	}
	
	private SubscriptionPk setPkWithId(String uniqSubId, Long uniqUserId, String platforms, String userName,Principal principal) {
		SubscriptionPk pk = SubscriptionPk.builder().uniqSubId(uniqSubId)
				.uniqUserId(uniqUserId).userName(userName)
				.platforms(platforms).build();

		return pk;
	}

}

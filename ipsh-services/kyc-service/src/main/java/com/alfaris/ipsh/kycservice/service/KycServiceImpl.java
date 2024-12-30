package com.alfaris.ipsh.kycservice.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
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

import com.alfaris.ipsh.kycservice.dto.KycRequestDto;
import com.alfaris.ipsh.kycservice.dto.ServiceResponse;
import com.alfaris.ipsh.kycservice.dto.ServiceResponses;
import com.alfaris.ipsh.kycservice.entity.KycRequest;
import com.alfaris.ipsh.kycservice.entity.KycRequestPk;
import com.alfaris.ipsh.kycservice.exception.RecordCreateException;
import com.alfaris.ipsh.kycservice.repo.KycRepo;
import com.alfaris.ipsh.kycservice.repo.spec.KycUserSpec;
import com.alfaris.ipsh.kycservice.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class KycServiceImpl implements kycService {
	
	private final KycRepo kycRepo;
	private final MessageSource messageSource;


	@Override
	public ServiceResponse createKycRequest(KycRequestDto kycRequestDto, Principal principal) throws RecordCreateException  {
		KycRequestPk pk= KycRequestPk.builder()
				.kycUniqueid(generateAlphanumericId(5))
				.createdBy(getUserName())
				.userId((long) ThreadLocalRandom.current().nextInt(1000, 10000))
				.build();
		KycRequest kycRequest=KycRequest.builder()
				.kycPk(pk)
				.createdAt(LocalDateTime.now())
				.userName(kycRequestDto.getUserName())
				.documentType(kycRequestDto.getDocumentType())
				.documentNumber(kycRequestDto.getDocumentNumber())
			   	.mobileNumber(kycRequestDto.getMobileNumber())
				.status(Constants.MESSAGE_STATUS.PROCESSED)
				.email(kycRequestDto.getEmail())
				.modifiedBy(getUserName())
				.comments(Constants.KYC_CMMT.CREATE_COMNT)
				.updatedAt(kycRequestDto.getUpdatedAt())
				.build();
			try { 
				
				kycRepo.save(kycRequest);
				 
				return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
						messageSource.getMessage("user.VAL0019", null, LocaleContextHolder.getLocale()), null);
				
			}catch (Exception e) {
				
			}
		
			
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("user.VAL0020", null, LocaleContextHolder.getLocale()), null);
		
	}

	@Override
	public ServiceResponse updateKycRequest(KycRequestDto kycRequestDto, Principal principal) {
		KycRequestPk pk= KycRequestPk.builder()
				.kycUniqueid(kycRequestDto.getKycUniqueid())
				.createdBy(getUserName())
				.userId(kycRequestDto.getUserId())
				.build();
		
		Optional<KycRequest> getDetails = kycRepo.findById(pk);
		if(getDetails.isEmpty()) {
			//throw new RecordCreateException();
		}
		
		KycRequest kycRequest=KycRequest.builder()
				.kycPk(pk)
				.createdAt(kycRequestDto.getCreatedAt())
				.userName(kycRequestDto.getUserName())
				.documentType(kycRequestDto.getDocumentType())
				.documentNumber(kycRequestDto.getDocumentNumber())
				.mobileNumber(kycRequestDto.getMobileNumber())
				.status("PROCESSING")
				.email(kycRequestDto.getEmail())
				.modifiedBy(getUserName())
				.comments(Constants.KYC_CMMT.UPDATE_COMNT)
				.updatedAt(LocalDateTime.now())
				.build();
			try { 
				kycRepo.save(kycRequest);
				
				return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
						messageSource.getMessage("user.VAL0021", null, LocaleContextHolder.getLocale()), null);
				
			}catch (Exception e) {
				
			}
			
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("user.VAL0022", null, LocaleContextHolder.getLocale()), null);
		
		
	}

	@Override
	public ServiceResponse verifyKyc(String kycUniqueid, String createdBy, Long userId, Principal principal) {
		try {
			KycRequestPk pk= KycRequestPk.builder()
					.kycUniqueid(kycUniqueid)
					.createdBy(createdBy)
					.userId(userId)
					.build();
			
			Optional<KycRequest> getDetails = kycRepo.findById(pk);
			if(getDetails.isEmpty()) {
				//throw new RecordCreateException();
			}
			
			KycRequest existingKycRequest = getDetails.get();
			
			if(existingKycRequest.getStatus().equals(Constants.MESSAGE_STATUS.VERIFIED)) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
						messageSource.getMessage("user.VAL0026", null, LocaleContextHolder.getLocale()), null);
				
				
			}
			else if(existingKycRequest.getStatus().equals(Constants.MESSAGE_STATUS.DELETED)) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
						messageSource.getMessage("user.VAL0028", null, LocaleContextHolder.getLocale()), null);
				
				
			}else if(existingKycRequest.getModifiedBy().equalsIgnoreCase(getUserName())) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
						messageSource.getMessage("user.VAL0027", null, LocaleContextHolder.getLocale()), null);
				
			}else if(existingKycRequest.getStatus().equals(Constants.MESSAGE_STATUS.DELETE)) {
				existingKycRequest.setStatus(Constants.MESSAGE_STATUS.DELETED);
				existingKycRequest.setModifiedBy(getUserName());
				existingKycRequest.setUpdatedAt(LocalDateTime.now());
				existingKycRequest.setComments(Constants.KYC_CMMT.DELETE_VERIFY_COMNT);
				kycRepo.save(existingKycRequest);
				return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
						messageSource.getMessage("user.VAL0031", null, LocaleContextHolder.getLocale()), null);
			}
			
			existingKycRequest.setStatus(Constants.MESSAGE_STATUS.APPROVED);
			existingKycRequest.setModifiedBy(getUserName());
			existingKycRequest.setUpdatedAt(LocalDateTime.now());
			existingKycRequest.setComments(Constants.KYC_CMMT.CREATE_VERIFY_COMNT);

			kycRepo.save(existingKycRequest);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0025", null, LocaleContextHolder.getLocale()), null);
			
			
		} catch (NoSuchMessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
				messageSource.getMessage("user.VAL0032", null, LocaleContextHolder.getLocale()), null);	

	}

	@Override
	public ServiceResponse deleteKycRequest(KycRequestDto kycRequestDto,
			Principal principal) {
		
		KycRequestPk pk= KycRequestPk.builder()
				.kycUniqueid(kycRequestDto.getKycUniqueid())
				.createdBy(kycRequestDto.getCreatedBy())
				.userId(kycRequestDto.getUserId())
				.build();
		
		Optional<KycRequest> getDetails = kycRepo.findById(pk);
		if(getDetails.isEmpty()) {
			
		}
		
		KycRequest existingKycRequest = getDetails.get();
		
		if(existingKycRequest.getStatus().equals(Constants.MESSAGE_STATUS.DELETED)) {
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("user.VAL0033", null, LocaleContextHolder.getLocale()), null);
		}
		
		existingKycRequest.setStatus(Constants.MESSAGE_STATUS.DELETE);
		existingKycRequest.setModifiedBy(getUserName());
		existingKycRequest.setUpdatedAt(LocalDateTime.now());
		existingKycRequest.setComments(Constants.KYC_CMMT.DELETE_COMNT);

		
		try {
			
			kycRepo.save(existingKycRequest);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0029", null, LocaleContextHolder.getLocale()), null);
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
				messageSource.getMessage("user.VAL0030", null, LocaleContextHolder.getLocale()), null);
		
	}

	@Override
	public ServiceResponses getKycRequestById(String kycUniqueid, String createdBy,Long userId,
			Principal principal) {
		KycRequestDto kycRequest =new KycRequestDto();
		
		KycRequestPk pk= KycRequestPk.builder()
				.kycUniqueid(kycUniqueid)
				.createdBy(createdBy)
				.userId(userId)
				.build();
		
		Optional<KycRequest> getDetails = kycRepo.findById(pk);
		if(getDetails.isEmpty()) {
			//throw new RecordCreateException();
		}
		
		KycRequest existingKycRequest = getDetails.get();
		BeanUtils.copyProperties(existingKycRequest, kycRequest);
		kycRequest.setUserId(userId);
		kycRequest.setKycUniqueid(kycUniqueid);
		kycRequest.setCreatedBy(createdBy);
		
		return new ServiceResponses(Constants.MESSAGE_STATUS.SUCCESS,
				messageSource.getMessage("user.VAL0034", null, LocaleContextHolder.getLocale()), List.of(kycRequest));
		 
	}
	
	String convertToJson(KycRequest kycRequest) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(kycRequest);
		} catch (Exception e) {
//			logger.error("Error : " + e.getMessage(), e);
		}
		return "";
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject searchByLimit(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Specification<KycRequest> spec = KycUserSpec.getUserSpec(searchParam);
			Page<KycRequest> usersKycList = kycRepo.findAll(spec, pageable);
			JSONArray array = new JSONArray();
			JSONArray countByStatus = countByStatus(spec);
			for (KycRequest userkyc : usersKycList) {
				
				JSONObject obj = new JSONObject();
				obj.put("userName", userkyc.getUserName());
				obj.put("comments", userkyc.getComments());
				obj.put("mobileNumber", userkyc.getMobileNumber());
				obj.put("userName", userkyc.getUserName());
				obj.put("status", userkyc.getStatus());
				obj.put("email", userkyc.getEmail());
				obj.put("documentType", userkyc.getDocumentType());
				obj.put("documentNumber", userkyc.getDocumentNumber());
				obj.put("userId", userkyc.getKycPk().getUserId());
				obj.put("createdBy", userkyc.getKycPk().getCreatedBy());
				obj.put("kycUniqueid", userkyc.getKycPk().getKycUniqueid());
				array.add(obj);
			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords", kycRepo.findAll(spec).size());
			result.put("iTotalRecords", kycRepo.findAll(spec).size());
			result.put("countByStatus", countByStatus);
		} catch (Exception e) {
			//logger.error("Error : " + e.getMessage(), e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray countByStatus(Specification<KycRequest> spec) {
		JSONArray array = new JSONArray();
		try {
			List<KycRequest> headerList = kycRepo.findAll(spec);
			Map<String, Long> countByStatus = headerList.stream()
					.collect(Collectors.groupingBy(KycRequest::getStatus, Collectors.counting()));
			for (String status : countByStatus.keySet()) {
				JSONObject obj = new JSONObject();
				obj.put("name", status);
				obj.put("count", countByStatus.get(status));
				array.add(obj);
			}
		} catch (Exception e) {
		
		}
		return array;
	}
	
	 private String generateAlphanumericId(int length) {
	        String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
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

}

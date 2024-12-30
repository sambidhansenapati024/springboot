package com.alfaris.ipsh.kycservice.service;

import java.security.Principal;

import org.json.simple.JSONObject;

import com.alfaris.ipsh.kycservice.dto.KycRequestDto;
import com.alfaris.ipsh.kycservice.dto.ServiceResponse;
import com.alfaris.ipsh.kycservice.dto.ServiceResponses;
import com.alfaris.ipsh.kycservice.exception.RecordCreateException;

public interface kycService {
	  ServiceResponse createKycRequest(KycRequestDto dto,Principal principal) throws RecordCreateException;
	  ServiceResponse updateKycRequest(KycRequestDto kycRequestDto,Principal principal);
	  ServiceResponse verifyKyc(String kycUniqueid,String createdBy,Long userId,Principal principal);
	  ServiceResponse deleteKycRequest(KycRequestDto kycRequestDto,Principal principal);
	  ServiceResponses getKycRequestById(String kycUniqueid,String createdBy,Long userId,Principal principal);
	  JSONObject searchByLimit(String searchParam, int parseInt, int parseInt2);
	  
}

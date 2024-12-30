package com.alfaris.ipsh.liquidity.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;

import com.alfaris.ipsh.liquidity.dto.PshLmsColDto;
import com.alfaris.ipsh.liquidity.dto.PshLmsColPkVerifyDto;
import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.entity.PshLmsColPK;
import com.alfaris.ipsh.liquidity.exception.RecordCreateException;
import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;

public interface SamaCollateralService {
	
	JSONObject searchByLimit(String searchParam, int start, int pageSize);

	ServiceResponse create(PshLmsColDto pshLmsColDto, Principal principal) throws RecordCreateException;
	
    ServiceResponse updatePayment(PshLmsColDto dto, Principal principal) throws RecordNotFoundException;
	
//	ServiceResponse verifyRecord(String bankidId, String productId, Date valueDate, Date timeStamp, Principal principle) throws RecordNotFoundException;
	
	ServiceResponse verifyRecord(PshLmsColPkVerifyDto pk, Principal principal) throws RecordNotFoundException;

    
	ServiceResponse delete(PshLmsColPkVerifyDto pk, Principal principal) throws RecordNotFoundException;
	
	PshLmsColDto getSamaCollateral(String bankidId, String productId, String valueDate, String timeStamp, Principal principle);

	List<String> getProductIds();


}

package com.alfaris.ipsh.liquidity.service;

import java.security.Principal;
import java.util.List;

import org.json.simple.JSONObject;

import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.entity.PshLqmPrm;
import com.alfaris.ipsh.liquidity.entity.PshLqmPrmPK;
import com.alfaris.ipsh.liquidity.exception.RecordCreateException;
import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;
import com.alfaris.ipsh.liquidity.exception.RecordUpdateException;


public interface LiquidityManagementParameterService {

	JSONObject searchByLimit(String searchParam, int start, int pageSize);
	
	List<String> getServiceIds(Principal principal);

	ServiceResponse create(PshLqmPrm entity, Principal principal) throws RecordCreateException ;

	JSONObject getDataById(String bankId, String serviceType, String currencyCode) throws RecordNotFoundException;

	ServiceResponse update(PshLqmPrm entity, Principal principal) throws RecordUpdateException, RecordNotFoundException ;

	ServiceResponse verify(PshLqmPrmPK entityPk, Principal principal) throws RecordNotFoundException;

	ServiceResponse delete(PshLqmPrmPK entityPk, Principal principal) throws RecordNotFoundException;

	

}

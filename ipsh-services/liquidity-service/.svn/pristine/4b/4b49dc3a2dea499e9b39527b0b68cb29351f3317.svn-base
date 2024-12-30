package com.alfaris.ipsh.liquidity.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;


public interface SamaLiquidityReportService {
	JSONObject searchByLimit(String searchParam, int start, int pageSize);
	ResponseEntity<Object> getFile(int documentId) throws RecordNotFoundException;
	
	ServiceResponse doReport(String dateRange);
}

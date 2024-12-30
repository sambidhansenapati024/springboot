package com.alfaris.ipsh.liquidity.service;

import org.json.simple.JSONObject;

public interface TransactionDetailsService {
	
	JSONObject getIncomingTransaction(String searcchParam, int start, int pageSize);
	
	JSONObject getOutgoingTransaction(String searcchParam, int start, int pageSize);


}

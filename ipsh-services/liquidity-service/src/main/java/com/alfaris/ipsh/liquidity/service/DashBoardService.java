package com.alfaris.ipsh.liquidity.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;



public interface DashBoardService {
	
	
	JSONArray getGraphData();

	JSONObject getDashBoardData();

}

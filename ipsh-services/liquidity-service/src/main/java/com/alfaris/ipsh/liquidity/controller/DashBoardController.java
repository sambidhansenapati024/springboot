package com.alfaris.ipsh.liquidity.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;
import com.alfaris.ipsh.liquidity.service.DashBoardService;

@RestController
@RequestMapping(value = "/dashBoard")
public class DashBoardController {
	
	@Autowired
	DashBoardService dashBoardSevice;
	
	
	@GetMapping("/graph")
	@PreAuthorize("@AC.hasAccess('DSHBRD', 'R')")
	public ResponseEntity<JSONArray> searchByPage() throws RecordNotFoundException {

		
		JSONArray list =dashBoardSevice.getGraphData();

		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/data")
	@PreAuthorize("@AC.hasAccess('DSHBRD', 'R')")
	public ResponseEntity<JSONObject> getDashBoardData() throws RecordNotFoundException {
		
		
		JSONObject data =dashBoardSevice.getDashBoardData();
		
		return new ResponseEntity<>(data, new HttpHeaders(), HttpStatus.OK);
	}
	
	

}

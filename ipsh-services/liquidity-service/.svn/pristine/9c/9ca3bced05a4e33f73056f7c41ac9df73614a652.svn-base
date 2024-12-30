package com.alfaris.ipsh.liquidity.controller;

import java.security.Principal;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;
import com.alfaris.ipsh.liquidity.service.SamaLiquidityReportService;


@RestController
@RequestMapping("/samaLiquidityReport")
public class SamaLiquidityReportController {
	
	@Autowired
	SamaLiquidityReportService samaLiquidityReportService;
	
	@GetMapping("/search")
	@PreAuthorize("@AC.hasAccess('SALIRE', 'R')")
	public ResponseEntity<JSONObject> searchByPage(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart, @RequestParam("iDisplayLength") String iDisplayLength,
			Principal principal) throws RecordNotFoundException {
		JSONObject list = samaLiquidityReportService.searchByLimit(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);

	}
	
	@GetMapping("/downloadById/{id}")
	@PreAuthorize("@AC.hasAccess('SALIRE', 'R')")
	public ResponseEntity<Object> downloadByID(@PathVariable("id") int documentId)throws RecordNotFoundException {
		return samaLiquidityReportService.getFile(documentId);	
	}
	
	
	@GetMapping("/generateReport")
	@PreAuthorize("@AC.hasAccess('SALIRE', 'R')")
	public ResponseEntity<ServiceResponse> downloadReport(@RequestParam("dateRange") String dateRange){

		return  new ResponseEntity<>(samaLiquidityReportService.doReport(dateRange), new HttpHeaders(), HttpStatus.OK);
		
	}
	
	
}

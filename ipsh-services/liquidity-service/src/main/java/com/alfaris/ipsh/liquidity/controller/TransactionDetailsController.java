package com.alfaris.ipsh.liquidity.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfaris.ipsh.liquidity.service.TransactionDetailsService;

@RestController
@RequestMapping(value="/transactionDetails")
public class TransactionDetailsController {
	
	@Autowired
	TransactionDetailsService detailsService;

	@GetMapping("/getIncomingTransact")
	@PreAuthorize("@AC.hasAccess('DSHBRD', 'R')")
	public JSONObject getIncomingTransactionDetails(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart, @RequestParam("iDisplayLength") String iDisplayLength) {
		return detailsService.getIncomingTransaction(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));
	}
	
	
	@GetMapping("/getOutgoingTransact")
	@PreAuthorize("@AC.hasAccess('DSHBRD', 'R')")
	public JSONObject getOutgoingTransactionDetails(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart, @RequestParam("iDisplayLength") String iDisplayLength) {
		return detailsService.getOutgoingTransaction(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));
	}
	

}

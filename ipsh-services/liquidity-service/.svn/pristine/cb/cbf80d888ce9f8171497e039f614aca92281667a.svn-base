package com.alfaris.ipsh.liquidity.controller;

import java.security.Principal;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfaris.ipsh.liquidity.dto.PshLmsColDto;
import com.alfaris.ipsh.liquidity.dto.PshLmsColPkVerifyDto;
import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.exception.RecordCreateException;
import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;
import com.alfaris.ipsh.liquidity.exception.RecordUpdateException;
import com.alfaris.ipsh.liquidity.service.SamaCollateralService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/samaCollateral")
public class SamaCollateralController {
	
	@Autowired
	SamaCollateralService service;
	
	
	@GetMapping("/search")
	@PreAuthorize("@AC.hasAccess('SAMACOL', 'R')")
	public ResponseEntity<JSONObject> searchByPage(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart, @RequestParam("iDisplayLength") String iDisplayLength)
			throws NumberFormatException, RecordNotFoundException {
		JSONObject list = service.searchByLimit(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("@AC.hasAccess('SAMACOL', 'C')")
	public ResponseEntity<ServiceResponse> create(@Valid @RequestBody PshLmsColDto pshLmsColDto, Principal principal)
			throws RecordCreateException {
		return new ResponseEntity<>(service.create(pshLmsColDto, principal), new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/getSamaCollateralById/{bankidId}/{productId}/{valueDate}/{timeStamp}")
	@PreAuthorize("@AC.hasAccess('SAMACOL', 'R')")
	public ResponseEntity<PshLmsColDto> get(@PathVariable("bankidId") String bankidId,
			@PathVariable("productId") String productId,@PathVariable("valueDate") String valueDate,@PathVariable("timeStamp") String timeStamp,
			Principal principle)throws  RecordNotFoundException {
		return new ResponseEntity<>(service.getSamaCollateral(bankidId, productId, valueDate, timeStamp,principle), new HttpHeaders(),
				HttpStatus.OK);
	
	}
	@PostMapping("/update")
	@PreAuthorize("@AC.hasAccess('SAMACOL', 'U')")
	public ResponseEntity<ServiceResponse> update(@Valid @RequestBody PshLmsColDto dto,
			Principal principal) throws RecordNotFoundException {
		return new ResponseEntity<>(service.updatePayment(dto, principal), new HttpHeaders(),
				HttpStatus.OK);
	}
	
	@PostMapping("/verify")
	@PreAuthorize("@AC.hasAccess('SAMACOL', 'V')")
	public ResponseEntity<ServiceResponse> verify(@RequestBody PshLmsColPkVerifyDto pk, Principal principal)
			throws ParseException, RecordNotFoundException, RecordUpdateException {
		return new ResponseEntity<>(service.verifyRecord(pk, principal), new HttpHeaders(), HttpStatus.OK);

	}
	
	@PutMapping("/delete")
	@PreAuthorize("@AC.hasAccess('SAMACOL', 'D')")
	public ResponseEntity<ServiceResponse> deleteSama(@RequestBody PshLmsColPkVerifyDto pk, Principal principal)
			throws RecordUpdateException, RecordNotFoundException {

		return new ResponseEntity<>(service.delete(pk, principal), new HttpHeaders(),
				HttpStatus.OK);
	}
	
	@GetMapping("/getProductIds")
	@PreAuthorize("@AC.hasAccess('SAMACOL', 'R')")
	public ResponseEntity<List<String>> getPaymentTypes(Principal principal) {
		List<String> list = service.getProductIds();
		return new ResponseEntity<List<String>>(list, new HttpHeaders(), HttpStatus.OK);
	}

}

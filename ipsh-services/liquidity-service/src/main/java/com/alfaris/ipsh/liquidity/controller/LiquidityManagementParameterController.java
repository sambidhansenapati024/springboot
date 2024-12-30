package com.alfaris.ipsh.liquidity.controller;

import java.security.Principal;
import java.util.List;

import org.json.simple.JSONObject;
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

import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.entity.PshLqmPrm;
import com.alfaris.ipsh.liquidity.entity.PshLqmPrmPK;
import com.alfaris.ipsh.liquidity.exception.RecordCreateException;
import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;
import com.alfaris.ipsh.liquidity.exception.RecordUpdateException;
import com.alfaris.ipsh.liquidity.service.LiquidityManagementParameterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/liqdtyMangParam")
public class LiquidityManagementParameterController {
	
	@Autowired
	LiquidityManagementParameterService liqdtyMangParamService;
	
	@GetMapping("/search")
	@PreAuthorize("@AC.hasAccess('LIMAPA', 'R')")
	public ResponseEntity<JSONObject> searchByPage(@RequestParam("searchParam") String searchParam, @RequestParam("iDisplayStart") String iDisplayStart, 
			@RequestParam("iDisplayLength") String iDisplayLength) throws NumberFormatException, RecordNotFoundException {
		
		JSONObject list = liqdtyMangParamService.searchByLimit(searchParam, Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength));
		return new ResponseEntity<JSONObject>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/getServiceIds")
	@PreAuthorize("@AC.hasAccess('LIMAPA', 'R')")
	public ResponseEntity<List<String>> getServiceIds(Principal principal) {
		List<String> list = liqdtyMangParamService.getServiceIds(principal);
		return new ResponseEntity<List<String>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("@AC.hasAccess('LIMAPA', 'C')")
	public ResponseEntity<ServiceResponse> create(@Valid @RequestBody PshLqmPrm entity, Principal principal) throws RecordCreateException {
		ServiceResponse response = liqdtyMangParamService.create(entity, principal);
		return new ResponseEntity<ServiceResponse>(response, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/getDataById/{bankId}/{productType}/{currencyCode}")
	@PreAuthorize("@AC.hasAccess('LIMAPA','R')")
	public ResponseEntity<JSONObject> getDataById(@PathVariable String bankId, @PathVariable String productType, 
			@PathVariable String currencyCode) throws RecordNotFoundException {
		
		JSONObject response = liqdtyMangParamService.getDataById(bankId, productType, currencyCode);
		return new ResponseEntity<JSONObject>(response, new HttpHeaders(), HttpStatus.OK);

	}
	
	@PutMapping
	@PreAuthorize("@AC.hasAccess('LIMAPA', 'U')")
	public ResponseEntity<ServiceResponse> update(@Valid @RequestBody PshLqmPrm entity, Principal principal)
			throws RecordUpdateException, RecordNotFoundException {
		ServiceResponse response = liqdtyMangParamService.update(entity, principal);
		return new ResponseEntity<ServiceResponse>(response, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/verify")
	@PreAuthorize("@AC.hasAccess('LIMAPA', 'V')")
	public ResponseEntity<ServiceResponse> verify(@RequestBody PshLqmPrmPK entityPk, Principal principal) throws RecordNotFoundException {
		ServiceResponse response = liqdtyMangParamService.verify(entityPk, principal);
		return new ResponseEntity<ServiceResponse>(response, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping("/delete")
	@PreAuthorize("@AC.hasAccess('LIMAPA', 'D')")
	public ResponseEntity<ServiceResponse> delete(@RequestBody PshLqmPrmPK entityPk, Principal principal) throws RecordNotFoundException {
		ServiceResponse response = liqdtyMangParamService.delete(entityPk, principal);
		return new ResponseEntity<ServiceResponse>(response, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	
	
}

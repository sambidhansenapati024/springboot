package com.alfaris.ipsh.kycservice.controller;

import java.security.Principal;

import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfaris.ipsh.kycservice.dto.KycRequestDto;
import com.alfaris.ipsh.kycservice.dto.ServiceResponse;
import com.alfaris.ipsh.kycservice.dto.ServiceResponses;
import com.alfaris.ipsh.kycservice.exception.RecordCreateException;
import com.alfaris.ipsh.kycservice.service.KycServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("kyc-serv")
@RequiredArgsConstructor
public class KycController {
	
	private final KycServiceImpl kycService;
	
	@PostMapping("/add")
	@PreAuthorize("@AC.hasAccess('NTFSCRN', 'C')")
	public ResponseEntity<ServiceResponse> addKycDetails(@Valid @RequestBody KycRequestDto kycRequestDto, Principal principal) throws RecordCreateException{
		return new ResponseEntity<>(kycService.createKycRequest(kycRequestDto, principal), new HttpHeaders(),
				HttpStatus.OK);
	}
	
 @GetMapping("/get-kyc-details/{kycUniqueid}/{createdBy}/{userId}")
 //@PreAuthorize("@AC.hasAccess('NTFSCRN', 'R')")
	public ResponseEntity<ServiceResponses> getKycDetails(@PathVariable String kycUniqueid,@PathVariable String createdBy,@PathVariable Long userId,Principal principal){
		return new ResponseEntity<>(kycService.getKycRequestById(kycUniqueid, createdBy, userId, principal), new HttpHeaders(),
				HttpStatus.OK);
	}
 
   @GetMapping("/get-kyc/search")
	@PreAuthorize("@AC.hasAccess('NTFSCRN', 'R')")
	public ResponseEntity<JSONObject> searchByPage(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {
		JSONObject list = kycService.searchByLimit(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));

		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}
   
   @PutMapping("/update")
   @PreAuthorize("@AC.hasAccess('NTFSCRN', 'U')")
   public ResponseEntity<ServiceResponse> updateKyc(@RequestBody KycRequestDto kycRequestDto ,Principal principal){
		return new ResponseEntity<>(kycService.updateKycRequest(kycRequestDto, principal), new HttpHeaders(),
				HttpStatus.OK);
	}
   
   @PostMapping("/verify/{kycUniqueid}/{createdBy}/{userId}")
   @PreAuthorize("@AC.hasAccess('NTFSCRN', 'V')")
   public ResponseEntity<ServiceResponse> verifyKyc(@PathVariable String kycUniqueid,@PathVariable String createdBy,@PathVariable Long userId,Principal principal){
		return new ResponseEntity<>(kycService.verifyKyc(kycUniqueid, createdBy, userId, principal), new HttpHeaders(),
				HttpStatus.OK);
	}
   
   @PutMapping("/delete")
   @PreAuthorize("@AC.hasAccess('NTFSCRN', 'D')")
   public ResponseEntity<ServiceResponse> deleteKyc(@RequestBody KycRequestDto kycRequestDto,Principal principal){
		return new ResponseEntity<>(kycService.deleteKycRequest(kycRequestDto, principal), new HttpHeaders(),
				HttpStatus.OK);
	}

}

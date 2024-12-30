package com.alfaris.ipsh.subscription.controller;
import java.security.Principal;
import java.util.List;

import org.json.simple.JSONObject;
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

import com.alfaris.ipsh.subscription.dto.ActionLogDto;
import com.alfaris.ipsh.subscription.dto.ServiceResponse;
import com.alfaris.ipsh.subscription.dto.ServiceResponses;
import com.alfaris.ipsh.subscription.dto.SubscriptionDto;
import com.alfaris.ipsh.subscription.entity.Platforms;
import com.alfaris.ipsh.subscription.exception.NoRecordPresentException;
import com.alfaris.ipsh.subscription.exception.RecordNotFoundException;
import com.alfaris.ipsh.subscription.exception.SubscriptionAlreadyExistsException;
import com.alfaris.ipsh.subscription.service.ActionServiceImpl;
import com.alfaris.ipsh.subscription.service.SubcriptionServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("sub-scribe")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {
	
	private final SubcriptionServiceImpl serviceImpl; 
	private final ActionServiceImpl actionServiceImpl;
	
	
	@PostMapping("/add-subsc") 
	@PreAuthorize("@AC.hasAccess('SUBSCRN', 'C')")
	public ResponseEntity<ServiceResponse> addSubDetails(@Valid @RequestBody  SubscriptionDto subscriptionDto, Principal principal) throws SubscriptionAlreadyExistsException{
		return new ResponseEntity<>(serviceImpl.addSubscription(subscriptionDto, principal), new HttpHeaders(),
				HttpStatus.OK);
	}
	@PostMapping("/verify/{uniqSubId}/{uniqUserId}/{platforms}/{userName}")
	@PreAuthorize("@AC.hasAccess('SUBSCRN', 'V')")
	public ResponseEntity<ServiceResponse> verifySubDetails(@PathVariable String uniqSubId,@PathVariable Long uniqUserId,@PathVariable String platforms,@PathVariable String userName,
			Principal principal) throws SubscriptionAlreadyExistsException, RecordNotFoundException{
		
		return new ResponseEntity<>(serviceImpl.verifySubscription(uniqSubId, uniqUserId, platforms, userName, principal), new HttpHeaders(),
				HttpStatus.OK);
	}
	@GetMapping("/get-sub-by-id/{uniqSubId}/{uniqUserId}/{platforms}/{userName}")
	@PreAuthorize("@AC.hasAccess('SUBSCRN', 'C')")
	public ResponseEntity<ServiceResponses> getSubDetailsById(@PathVariable String uniqSubId,@PathVariable Long uniqUserId,@PathVariable String platforms,@PathVariable String userName,
			Principal principal) throws SubscriptionAlreadyExistsException, RecordNotFoundException{
		
		return new ResponseEntity<>(serviceImpl.getSubById(uniqSubId, uniqUserId, platforms, userName, principal), new HttpHeaders(),
				HttpStatus.OK);
	}
	
	@PutMapping("/update")
	@PreAuthorize("@AC.hasAccess('SUBSCRN', 'U')")
	public ResponseEntity<ServiceResponse> updateSubDetails(@Valid @RequestBody  SubscriptionDto subscriptionDto, Principal principal) throws NoRecordPresentException{
		return new ResponseEntity<>(serviceImpl.updateSubscription(subscriptionDto, principal), new HttpHeaders(),
				HttpStatus.OK); 
	} 
	
	@PutMapping("/delete")
	@PreAuthorize("@AC.hasAccess('SUBSCRN', 'D')")
	public ResponseEntity<ServiceResponse> deleteSubDetails( @RequestBody  SubscriptionDto subscriptionDto, Principal principal) throws NoRecordPresentException{
		return new ResponseEntity<>(serviceImpl.deleteSubscription(subscriptionDto, principal), new HttpHeaders(),
				HttpStatus.OK);
	} 
	
	 @GetMapping("/get-subsc/search")
		@PreAuthorize("@AC.hasAccess('SUBSCRN', 'R')")
		public ResponseEntity<JSONObject> searchByPage(@RequestParam("searchParam") String searchParam,
				@RequestParam("iDisplayStart") String iDisplayStart,
				@RequestParam("iDisplayLength") String iDisplayLength) {
			JSONObject list = serviceImpl.searchByLimit(searchParam, Integer.parseInt(iDisplayStart),
					Integer.parseInt(iDisplayLength));
			
			return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
		}
	 
	 @GetMapping("/get-action-log/search")
		@PreAuthorize("@AC.hasAccess('SUBSCRN', 'R')")
		public ResponseEntity<JSONObject> searchByPage2(@RequestParam("searchParam") String searchParam,
				@RequestParam("iDisplayStart") String iDisplayStart, @RequestParam("iDisplayLength") String iDisplayLength) {
			JSONObject list = actionServiceImpl.getUserLog(searchParam, iDisplayStart, iDisplayLength);

			return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
		}
	 
	 @GetMapping("/get-platform")
		@PreAuthorize("@AC.hasAccess('SUBSCRN', 'R')")
	 public ResponseEntity<List<Platforms>> getAllPlatform(){
		 return new ResponseEntity<>(serviceImpl.getAllPlatforms(),HttpStatus.OK);
		  
	 }
	 @PostMapping("/excelDownload")
	 @PreAuthorize("@AC.hasAccess('SUBSCRN', 'R')")
	 public ResponseEntity<JSONObject> downloadErrorData(@RequestBody ActionLogDto usersearch) {
	     JSONObject response = actionServiceImpl.generateErrorDataExcel(usersearch);
	     return new ResponseEntity<>(response, HttpStatus.OK);
	 }

	 @PostMapping("/pdfDownload")
	 @PreAuthorize("@AC.hasAccess('SUBSCRN', 'R')")
	 public ResponseEntity<JSONObject> downloadPdfData(@RequestBody ActionLogDto usersearch) {
	     JSONObject response = actionServiceImpl.generatePdfData(usersearch);
	     return new ResponseEntity<>(response, HttpStatus.OK);
	 }

}

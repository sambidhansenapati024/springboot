package com.alfaris.ipsh.authservice.controller;

import com.alfaris.ipsh.authservice.service.UserService;
import com.alfaris.ipsh.authservice.util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alfaris.ipsh.authservice.dto.OauthDTO;
import com.alfaris.ipsh.authservice.dto.RefreshToken;

@RestController
@RequestMapping("/oauth")
public class OauthController {

	private static Logger logger = LogManager.getLogger(OauthController.class);

	@Value("${psh.keycloak.clientid}")
	private String clientid;

	@Value("${psh.keycloak.client.secret}")
	private String clientSecret;

	@Value("${psh.keycloak.url}")
	private String url;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UserService userService;

	@PostMapping("/token")
	public ResponseEntity<JSONObject> getToken(@RequestBody OauthDTO auth , HttpServletRequest request) {
		JSONObject list = new JSONObject();
		try {

			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
			requestBody.add("client_id", clientid);
			requestBody.add("client_secret", clientSecret);
			requestBody.add("scope", "openid");
			requestBody.add("grant_type", "password");
			requestBody.add("username", auth.getUsername());
			requestBody.add("password", auth.getPassword());

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			JSONParser parser = new JSONParser();
			JSONObject res = (JSONObject) parser.parse(response.getBody());

			System.out.println(res);
			String accessToken = (String) res.get("access_token");
			if (StringUtils.hasLength(accessToken)) {
				userService.saveUserLog(auth.getUsername(), Constants.MESSAGE_STATUS.SUCCESS , request);
			}
//			else{
//				userService.saveUserLog(auth.getUsername(), Constants.MESSAGE_STATUS.FAILED , request);
//			}

			return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			userService.saveUserLog(auth.getUsername(), Constants.MESSAGE_STATUS.FAILED , request);
		}
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);

	}
	
	@PostMapping("/client/token")
	public ResponseEntity<JSONObject> getClentToken(@RequestBody OauthDTO auth) {
		JSONObject list = new JSONObject();
		try {

			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
			requestBody.add("client_id", auth.getUsername());
			requestBody.add("client_secret", auth.getPassword());
			requestBody.add("scope", "openid");
			requestBody.add("grant_type", auth.getGrant_type());

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			JSONParser parser = new JSONParser();
			JSONObject res = (JSONObject) parser.parse(response.getBody());
			return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);

	}
	
	@PostMapping("/token/refresh")
	public ResponseEntity<JSONObject> getRefreshToken(@RequestBody RefreshToken auth) {
		JSONObject list = new JSONObject();
		try {

			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
			requestBody.add("client_id", clientid);
			requestBody.add("client_secret", clientSecret);
			requestBody.add("scope", "openid");
			requestBody.add("grant_type", "refresh_token");
			requestBody.add("refresh_token", auth.getRefreshToken());

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			JSONParser parser = new JSONParser();
			JSONObject res = (JSONObject) parser.parse(response.getBody());
			return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);

	}
}

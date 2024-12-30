package com.alfaris.ipsh.authservice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfaris.ipsh.authservice.dto.AuditKeyDTO;
import com.alfaris.ipsh.authservice.dto.ServiceResponse;
import com.alfaris.ipsh.authservice.entity.PshCasHry;
import com.alfaris.ipsh.authservice.entity.PshColCnf;
import com.alfaris.ipsh.authservice.exception.RecordCreateException;
import com.alfaris.ipsh.authservice.exception.RecordNotFoundException;
import com.alfaris.ipsh.authservice.exception.RecordUpdateException;
import com.alfaris.ipsh.authservice.service.UserService;
import com.alfaris.ipsh.authservice.util.Constants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
//@RefreshScope
@RequestMapping("/user")
public class UserController {

	private static Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	UserService userService;

//	@Autowired
//	ConsumerTokenServices tokenServices;
//
//	@Autowired
//	TokenStore tokenStore;

	@Autowired
	MessageSource messageSource;

	@Value("${log.path}")
	String logFilePath;


//	@GetMapping//================
//	public ResponseEntity<List<Users>> getAllUsers() {
//		List<Users> list = userService.getAllUsers();
//		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@GetMapping("/{id}")//==========
//	public ResponseEntity<Object> getUserById(@PathVariable("id") String id) throws RecordNotFoundException {
//		logger.info("Get user id : {}", id);
//		Users entity = userService.getUserById(id);
//		return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@PostMapping//==============
//	public ResponseEntity<ServiceResponse> create(@Valid @RequestBody Users user, )
//			throws RecordCreateException {
//		return new ResponseEntity<>(userService.createUser(user, principal), new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@PutMapping//=============
//	public ResponseEntity<ServiceResponse> update(@Valid @RequestBody Users user, )
//			throws RecordNotFoundException, RecordUpdateException {
//		return new ResponseEntity<>(userService.updateUser(user, principal), new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@DeleteMapping("/{id}")//==================
//	public ResponseEntity<ServiceResponse> deleteUserById(@PathVariable("id") String id, )
//			throws RecordNotFoundException, RecordUpdateException {
//		return new ResponseEntity<>(userService.deleteUserById(id, principal), new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@GetMapping("/search") //===========
//	public ResponseEntity<JSONObject> searchByPage(@RequestParam("searchParam") String searchParam,
//			@RequestParam("iDisplayStart") String iDisplayStart,
//			@RequestParam("iDisplayLength") String iDisplayLength) {
//		JSONObject list = userService.searchByLimit(searchParam, Integer.parseInt(iDisplayStart),
//				Integer.parseInt(iDisplayLength));
//
//		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
//	}

	@GetMapping("/accessScreenList")
	public ResponseEntity<JSONArray> accessScreenList() {
		JSONArray result = userService.accessScreenList(getUserName());
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/userInfo")
	public ResponseEntity<JSONObject> getUserInfo() {
		String userName = getUserName();
		JSONObject result = userService.getUserInfo(userName);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	private String getUserName() {
		Jwt p = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> claims = p.getClaims();
		return (String) claims.get("preferred_username");
	}

	@GetMapping("/theme")
	public ResponseEntity<String> getUserThemeById() throws RecordNotFoundException {
		logger.info("Get user id : {}", getUserName());
		return new ResponseEntity<>(userService.getUserThemeById(getUserName()), new HttpHeaders(), HttpStatus.OK);

	}

	@PostMapping("/theme")
	public ResponseEntity<ServiceResponse> createUserTheme(@RequestBody JSONObject jsonData )
			throws RecordNotFoundException, RecordCreateException {
		return new ResponseEntity<>(userService.saveTheme(jsonData), new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/theme")
	public ResponseEntity<ServiceResponse> updateUserTheme(@RequestBody JSONObject jsonData )
			throws RecordNotFoundException, RecordUpdateException {
		return new ResponseEntity<>(userService.updateTheme(jsonData), new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/oauth/token")
	public ServiceResponse revokeToken(HttpServletRequest request ) {
		try {

			userService.saveUserLog( getUserName(), Constants.USERLOG.USER_LOG_S , request);



//			String authorization = request.getHeader("Authorization");
//			if (authorization != null && authorization.contains("Bearer")) {
//				String tokenId = authorization.substring("Bearer".length() + 1);
//				System.out.println("tokenId : " + tokenId);
//				OAuth2AccessToken token = tokenStore.readAccessToken(tokenId);
//				tokenStore.removeRefreshToken(token.getRefreshToken());
//				tokenServices.revokeToken(tokenId);
//				userService.logOn( getUserName());
//			}
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0013", null, LocaleContextHolder.getLocale()), null);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ServiceResponse("user.VAL0014",
					messageSource.getMessage("user.VAL0014", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@PostMapping("/audit")
	public ResponseEntity<ServiceResponse> createAuditLog(@RequestBody PshCasHry audit ) {
		return new ResponseEntity<>(userService.saveAuditData(audit), new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/audit/get")
	public ResponseEntity<PshCasHry> getAuditLog(@RequestBody AuditKeyDTO auditKey )
			throws RecordNotFoundException {
		return new ResponseEntity<>(userService.getAuditData(auditKey), new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/audit/search")
	public ResponseEntity<JSONObject> searchAudotData(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {
		JSONObject list = userService.searchAuditData(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));

		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "/logfile/{file_name}")
	public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) throws Exception {
		File initialFile = new File(logFilePath + File.separator + getFileName(fileName));
		try (InputStream is = new FileInputStream(initialFile)) {
			// copy it to response’s OutputStream
			// org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			String filename = "attachment;" + "filename = " + fileName;
			response.setHeader("Content - Disposition", "attachment; filename = " + filename + ".json");
			response.flushBuffer();
		} catch (Exception ex) {
			logger.error("Error : " + ex.getMessage(), ex);
			throw ex;
		}
	}

	private String getFileName(String fileName) {
		String[] folderList = fileName.split("&");
		String path = "";
		for (int i = 0; i < folderList.length; i++) {
			if (StringUtils.isEmpty(path))
				path = folderList[i];
			else
				path = path + File.separator + folderList[i];
		}
		return path;
	}

	@GetMapping("/applicationLog")
	public ResponseEntity<JSONObject> applicationLog() {
		JSONObject list = userService.getApplicationLog();

		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/tableConfig")
	public ResponseEntity<PshColCnf> getTableConfig(@RequestParam("screenId") String screenId)
			throws RecordNotFoundException {
		logger.info("Get user id : {}", getUserName());
		return new ResponseEntity<>(userService.getTableConfig(getUserName(), screenId), new HttpHeaders(),
				HttpStatus.OK);
 
	}

	@PostMapping("/tableConfig")
	public ResponseEntity<ServiceResponse> saveTableConfig(@RequestBody PshColCnf config )
			throws RecordNotFoundException, RecordCreateException {
		return new ResponseEntity<>(userService.saveOrUpdateTableConfig(config), new HttpHeaders(), HttpStatus.OK);
	}
}

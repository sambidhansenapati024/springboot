/****************************************************************************
 *                              COPYRIGHT NOTICE
*
*                      Copyright(@2006) by Interland Technology Services PVT. LTD **
*
*      This program is used to monitor the stream control and Stop/Start
*      the streams. The program and related materials are confidential and
*      proprietary of Interland Technology Services PVT. LTD and no part of these materials
*      should be reproduced, published in any forms without the written
*      approval of INTERLAND
*
** Project Name         : iPSH
** Program description  : SecurityController
** Version No           : 1.0.0
** Author               : Adarsh,Harichand,Yadhu
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfaris.ipsh.security.dto.SearchUserLog;
import com.alfaris.ipsh.security.dto.ServiceResponse;
import com.alfaris.ipsh.security.dto.UsersDto;
import com.alfaris.ipsh.security.entity.Group;
import com.alfaris.ipsh.security.entity.GroupScreen;
import com.alfaris.ipsh.security.entity.Users;
import com.alfaris.ipsh.security.exception.RecordCreateException;
import com.alfaris.ipsh.security.exception.RecordNotFoundException;
import com.alfaris.ipsh.security.exception.RecordUpdateException;
import com.alfaris.ipsh.security.service.SecurityGroupService;
import com.alfaris.ipsh.security.service.SecurityPermissionService;
import com.alfaris.ipsh.security.service.SecurityUserLogService;
import com.alfaris.ipsh.security.service.SecurityUserService;
import com.alfaris.ipsh.security.util.Constants;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping(value = "/security")
public class SecurityController {

	@Value("${GROUP}")
	private String buttonForGroup;

	@Autowired
	SecurityPermissionService securityPermissionService;

	@Autowired
	SecurityUserService securityUserService;

	@Autowired
	SecurityGroupService securityGroupService;

	@Autowired
	SecurityUserLogService securityUserLogService;

	private static Logger logger = LogManager.getLogger(SecurityController.class);

	@GetMapping("/current")
	public Principal getUser(Principal principal) {
		return principal;
	}

	@PostMapping("/securityPermission/groupDropDown")
	public ResponseEntity<JSONArray> groupDropDown(Principal principal) throws RecordNotFoundException {

		JSONArray result = securityPermissionService.getGrpIdName(getUserName());
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/securityPermission/getTableData")
	@PreAuthorize("@AC.hasAccess('SECACSCR', 'R')")
	public ResponseEntity<JSONArray> getTableData(@RequestBody String dataString)
			throws RecordNotFoundException, ParseException {

		JSONArray result = securityPermissionService.getGrpScrPermission(dataString);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/securityPermission/saveGroupScreenPermission")
	@PreAuthorize("@AC.hasAccess('SECACGRP', 'C')")
	public ResponseEntity<ServiceResponse> saveGroupScreenPermission(@RequestBody String reqObj)
			throws RecordNotFoundException, ParseException {

		ServiceResponse result = securityPermissionService.saveGroupScreenPermission(reqObj);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/securityPermission/createGroup")
	@PreAuthorize("@AC.hasAccess('SECACGRP', 'C')")
	public ResponseEntity<ServiceResponse> createGroup(@Valid @RequestBody Group groupDetails, Principal principal)
			throws RecordNotFoundException, ParseException {

		ServiceResponse result = securityPermissionService.createGroup(groupDetails, principal, buttonForGroup);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/securityUser/search")
	@PreAuthorize("@AC.hasAccess('SECACUSR', 'R')")
	public ResponseEntity<JSONObject> searchByPage(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {
		JSONObject list = securityUserService.searchByLimit(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));

		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/securityUser/{id}")
	@PreAuthorize("@AC.hasAccess('SECACUSR', 'R')")
	public ResponseEntity<Object> getUserById(@PathVariable("id") String id) {
		try {
			logger.info("Get user id : {}", id);
			Users entity = securityUserService.getUserById(id);
			return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/securityUser")
	public ResponseEntity<List<Users>> getAllUsers() {
		List<Users> list = securityUserService.getAllUsers();

		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/securityUser/getBankIdDropDown")
	@PreAuthorize("@AC.hasAccess('SECACUSR', 'R')")
	public ResponseEntity<JSONObject> getBankIdDropDown() {
		return new ResponseEntity<>(securityUserService.getBankIdDropDown(), new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/securityUser")
	@PreAuthorize("@AC.hasAccess('SECACUSR', 'C')")
	public ResponseEntity<ServiceResponse> create(@Valid @RequestBody UsersDto user, Principal principal)
			throws RecordCreateException {
		return new ResponseEntity<>(securityUserService.createUser(user, principal), new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/securityUser")
	@PreAuthorize("@AC.hasAccess('SECACUSR', 'U')")
	public ResponseEntity<ServiceResponse> update(@Valid @RequestBody UsersDto user, Principal principal)
			throws RecordNotFoundException, RecordUpdateException {
		return new ResponseEntity<>(securityUserService.updateUser(user, principal), new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/securityUser/{id}")
	@PreAuthorize("@AC.hasAccess('SECACUSR', 'D')")
	public ResponseEntity<ServiceResponse> deleteUserById(@PathVariable("id") String id, Principal principal)
			throws RecordNotFoundException, RecordUpdateException {
		return new ResponseEntity<>(securityUserService.deleteUserById(id, principal), new HttpHeaders(),
				HttpStatus.OK);
	}

	@GetMapping("/securityUser/groupDropDown")
	@PreAuthorize("@AC.hasAccess('SECACUSR', 'R')")
	public ResponseEntity<JSONObject> getGroupIdDrop() {
		JSONObject serviceIds = securityUserService.getServiceIdDrop();

		return new ResponseEntity<>(serviceIds, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/securityUser/verify")
	@PreAuthorize("@AC.hasAccess('SECACUSR', 'V')")
	public ResponseEntity<ServiceResponse> verify(@RequestBody String userId, Principal principal)
			throws RecordNotFoundException {
		return new ResponseEntity<>(securityUserService.verifyUser(userId, principal), new HttpHeaders(),
				HttpStatus.OK);
	}
	
	@GetMapping("/securityUser/departmentDropDown")
//	@PreAuthorize("@AC.hasAccess('SECACUSR', 'R')")
	public ResponseEntity<JSONArray> getDepartmentDropDown() {
		JSONArray departments = securityUserService.getDepartmentDropDown();

		return new ResponseEntity<>(departments, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/securityGroup/userGroupSearch")
	@PreAuthorize("@AC.hasAccess('SECACGRP', 'R')")
	public ResponseEntity<JSONObject> searchByPageGroup(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {

		JSONObject list = new JSONObject();
		try {
			list = securityGroupService.searchGroupByLimit(searchParam, Integer.parseInt(iDisplayStart),
					Integer.parseInt(iDisplayLength));
		} catch (NumberFormatException e) {
			logger.error("Error : " + e.getMessage(), e);
		}

		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	/*
	 * @PostMapping("/securityGroup/createGroup") public ResponseEntity<JSONObject>
	 * createGroup(@RequestBody Group group) { JSONObject result = new JSONObject();
	 * try { result = securityGroupService.createGroup(group); } catch (Exception e)
	 * { logger.error("Error : " + e.getMessage(), e); } return new
	 * ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK); }
	 */

	@PutMapping("/securityGroup/updateGroup")
	@PreAuthorize("@AC.hasAccess('SECACGRP', 'U')")
	public ResponseEntity<ServiceResponse> update(@Valid @RequestBody Group group, Principal principal)
			throws RecordUpdateException, RecordNotFoundException {
		ServiceResponse result = securityGroupService.updateGroup(group, principal);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/securityGroup/deleteGroup")
	@PreAuthorize("@AC.hasAccess('SECACGRP', 'D')")
	public ResponseEntity<ServiceResponse> deleteGroup(@RequestBody Group group, Principal principal)
			throws RecordNotFoundException {
		ServiceResponse result = securityGroupService.deleteGroup(group.getGroupId(), principal);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/securityGroup/verifyGroup")
	@PreAuthorize("@AC.hasAccess('SECACGRP', 'V')")
	public ResponseEntity<ServiceResponse> verifyGroup(@RequestBody Group group, Principal principle)
			throws RecordNotFoundException {
		ServiceResponse result = securityGroupService.verifyGroup(group.getGroupId(), principle);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/securityGroup/getDataById/{groupId}")
	@PreAuthorize("@AC.hasAccess('SECACGRP', 'R')")
	public ResponseEntity<Object> getGroupById(@PathVariable("groupId") String groupId) {
		try {
			Group entity = securityGroupService.getDataById(groupId);
			return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/securityGroup/reActivateGroup")
	@PreAuthorize("@AC.hasAccess('SECACGRP', 'V')")
	public ResponseEntity<ServiceResponse> reActivateGroup(@RequestBody Group group) throws RecordNotFoundException {
		ServiceResponse result = securityGroupService.reActivateGroup(group.getGroupId());
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/securityGroup/byNotificationId")
	public ResponseEntity<Group> getbyNotificationId(@RequestParam("notificationId") String notificationId)
			throws RecordNotFoundException {
		Group obj = securityGroupService.getByNotificationId(notificationId);
		return new ResponseEntity<>(obj, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/securityUserLog/search")
	@PreAuthorize("@AC.hasAccess('SECUSRLG', 'R')")
	public ResponseEntity<JSONObject> searchByPage2(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart, @RequestParam("iDisplayLength") String iDisplayLength,
			Principal userId) {
		String strUserId = getUserName();
		JSONObject list = securityUserLogService.getUserLog(searchParam, iDisplayStart, iDisplayLength, strUserId);

		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/securityUserLog/excelDownload")
	@PreAuthorize("@AC.hasAccess('SECUSRLG', 'R')")
	public ResponseEntity<JSONObject> downloadErrorData(@RequestBody SearchUserLog usersearch) throws Exception {
		JSONObject obj = new JSONObject();

		try {
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet(Constants.USER_LOG_SHEET_NAME);
			
			XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
			XSSFFont headerFont=(XSSFFont) workbook.createFont();
//			headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			headerFont.setBold(true);
			headerStyle.setFont(headerFont);
			
			Row header = sheet.createRow(0);
			
			header.createCell(0).setCellValue(Constants.USER_LOG_MSG_USER_ID);
			header.getCell(0).setCellStyle(headerStyle);
			
			header.createCell(1).setCellValue(Constants.USER_LOG_MSG_LOGIN_TIME);
			header.getCell(1).setCellStyle(headerStyle);
			
			header.createCell(2).setCellValue(Constants.USER_LOG_MSG_LOGIN_STATUS);
			header.getCell(2).setCellStyle(headerStyle);
			
			header.createCell(3).setCellValue(Constants.USER_LOG_MSG_REASON);
			header.getCell(3).setCellStyle(headerStyle);
			
			header.createCell(4).setCellValue(Constants.USER_LOG_MSG_IP_ADDRESS);
			header.getCell(4).setCellStyle(headerStyle);
			
			header.createCell(5).setCellValue(Constants.USER_LOG_MSG_SYSTEM_DATE);
			header.getCell(5).setCellStyle(headerStyle);
			
			sheet = securityUserLogService.getUserLogTableExcelSheet(usersearch, sheet);
			sheet.autoSizeColumn((short) 0);
			sheet.autoSizeColumn((short) 1);
			sheet.autoSizeColumn((short) 2);
			sheet.autoSizeColumn((short) 3);
			sheet.autoSizeColumn((short) 4);
			sheet.autoSizeColumn((short) 5);
			
			ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
			workbook.write(fileOutputStream);

			byte[] isr = fileOutputStream.toByteArray();
			byte[] encoded = Base64.getEncoder().encode(isr);
			String enc = new String(encoded);
			String fileName = Constants.USER_LOG_XLS_FILE_NAME;
			obj.put(Constants.DATA, enc);
			obj.put(Constants.FILE_NAME, fileName);
			obj.put(Constants.CONTENT_TYPE, Constants.EXCEL_CONTENT_TYPE);
			return new ResponseEntity<JSONObject>(obj, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<JSONObject>(obj, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/securityUserLog/pdfDownload")
	@PreAuthorize("@AC.hasAccess('SECUSRLG', 'R')")
	public ResponseEntity<JSONObject> downloadPdfData(@RequestBody SearchUserLog usersearch) throws Exception {
		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
		JSONObject obj = new JSONObject();
		Resource resource = new ClassPathResource("logo.png");
		InputStream inputStream = resource.getInputStream();
		try {
			ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
			Document document = new Document();
			PdfWriter.getInstance(document, fileOutputStream);
			document.open();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			addImage(document, bdata);
			Paragraph paragraph = new Paragraph(Constants.USER_LOG_PDF_NAME, catFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			document.add(Chunk.NEWLINE);
			PdfPTable table = new PdfPTable(6);
			Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
			table.setWidthPercentage(100);
			float[] columnWidths = { 1.2f, 2.2f, 2f, 1.8f, 1.7f, 1.7f };
			table.setWidths(columnWidths);
			PdfPCell cell1 = new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_USER_ID, font));
			PdfPCell cell2 = new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_LOGIN_TIME, font));
			PdfPCell cell3 = new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_LOGIN_STATUS, font));
			PdfPCell cell4 = new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_REASON, font));
			PdfPCell cell5 = new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_IP_ADDRESS, font));
			PdfPCell cell6 = new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_SYSTEM_DATE, font));
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.setHeaderRows(1);
			table = securityUserLogService.getUserLogTableData(usersearch, table);
			document.add(table);
			document.close();
			byte[] isr = fileOutputStream.toByteArray();
			byte[] encoded = Base64.getEncoder().encode(isr);
			String enc = new String(encoded);
			String fileName = Constants.USER_LOG_PDF_FILE_NAME;
			obj.put(Constants.DATA, enc);
			obj.put(Constants.FILE_NAME, fileName);
			obj.put(Constants.CONTENT_TYPE, Constants.PDF_CONTENT_TYPE);

			return new ResponseEntity<JSONObject>(obj, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<JSONObject>(obj, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/securityPermission/notifyGroup")
	public ResponseEntity<List<String>> notifyGroup(@RequestParam("permissionType") String permissionType,
			@RequestParam("screenId") String screenId) throws RecordNotFoundException, ParseException {

		List<String> result = securityPermissionService.getGroupListForNotification(permissionType, screenId);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/securityUser/bankId")
	@PreAuthorize("@AC.hasAccess('SECACUSR', 'R')")
	public String getBankId(@RequestParam("userId") String userId) {

		return securityUserService.getBankId(userId);
	}

	@GetMapping("/securityPermission/buttonPermission")
	public ResponseEntity<GroupScreen> getButtonPermission(@RequestParam("groupId") String groupId,
			@RequestParam("screenId") String screenId) throws RecordNotFoundException, ParseException {

		GroupScreen result = securityPermissionService.getButtonPermission(groupId, screenId);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}

	public void addImage(Document document, byte[] imageArray) {
		try {
			Image logo = Image.getInstance(imageArray);
			logo.scaleAbsolute(80f, 100f);
			document.add(logo);
		} catch (DocumentException e) {
			logger.error("UserController in method addImage() " + e.getMessage(), e);
		} catch (MalformedURLException e) {
			logger.error("UserController in method addImage() " + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("UserController in method addImage() " + e.getMessage(), e);
		}
	}
	
	@GetMapping("/securityGroups/getHomeScreenUrlDropDown")
	public ResponseEntity<JSONArray> getHomeScreenUrlDropDown() {
		JSONArray homeScreenUrl = securityUserService.getHomeScreenUrlDropDown();

		return new ResponseEntity<>(homeScreenUrl, new HttpHeaders(), HttpStatus.OK);
	}
	
	//added for security upgrade
	@GetMapping("/securityPermission/getPermission")
	public ResponseEntity<GroupScreen> getPermission(@RequestParam("screenId") String screenId,Principal principal) throws RecordNotFoundException, ParseException {
		Users entity = securityUserService.getUserById(getUserName());
		GroupScreen result = securityPermissionService.getButtonPermission(entity.getGroup().getGroupId(), screenId);
		return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
	}
	
	private String getUserName() {
		Jwt p = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> claims = p.getClaims();
        return (String) claims.get("preferred_username");
	}
}

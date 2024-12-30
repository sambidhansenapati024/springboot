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
** Program description  : SecurityPermissionServiceImpl
** Version No           : 1.0.0
** Author               : Adarsh
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.service;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.security.client.AuditLogUtil;
import com.alfaris.ipsh.security.dto.GroupDetails;
import com.alfaris.ipsh.security.dto.ServiceResponse;
import com.alfaris.ipsh.security.entity.Group;
import com.alfaris.ipsh.security.entity.GroupScreen;
import com.alfaris.ipsh.security.entity.GroupScreenPK;
import com.alfaris.ipsh.security.entity.Screen;
import com.alfaris.ipsh.security.entity.Users;
import com.alfaris.ipsh.security.exception.RecordNotFoundException;
import com.alfaris.ipsh.security.repository.GroupRepository;
import com.alfaris.ipsh.security.repository.GroupScreenRepository;
import com.alfaris.ipsh.security.repository.ScreenRepository;
import com.alfaris.ipsh.security.repository.UserRepository;
import com.alfaris.ipsh.security.repository.specification.SecurityPermissionSpec;
import com.alfaris.ipsh.security.util.AuditFuctions;
import com.alfaris.ipsh.security.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SecurityPermissionServiceImpl implements SecurityPermissionService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	GroupScreenRepository groupScreenRepository;

	@Autowired
	ScreenRepository screenRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	AuditLogUtil auditLogUtil;
	
	@Autowired
	NotificationService notificationService;


	private static Logger logger = LogManager.getLogger(SecurityPermissionServiceImpl.class);

	@Override
	public JSONArray getGrpIdName(String userId) {
		JSONArray resArray = new JSONArray();
		List<Object[]> groupIdNameArr = null;
		try {
			Optional<Users> userEntity = userRepository.findById(userId);
			Users users = userEntity.get();
			String strGroupId = users.getGroup().getGroupId();
			Optional<Group> groupEntity = groupRepository.findById(strGroupId);
			Group group = groupEntity.get();
			int groupType = group.getGroupType();
			groupIdNameArr = groupRepository.findAll(Constants.GROUP_ID, Constants.GROUP_NAME, Constants.GROUP_TYPE);
			for (Object[] obj : groupIdNameArr) {
				String groupId = (String) obj[0];
				String groupName = (String) obj[1];
				int groupTypeAct = (int) obj[2];
				JSONObject grpObj = new JSONObject();
				grpObj.put(Constants.GROUP_ID, groupId);
				grpObj.put(Constants.GROUP_NAME, groupName);
				grpObj.put(Constants.GROUP_TYPE, groupTypeAct);
				resArray.add(grpObj);
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return resArray;
	}

	@Override
	public JSONArray getGrpScrPermission(String dataString) throws ParseException {
		JSONArray resArray = new JSONArray();
		JSONParser parser = null;

		try {
			parser = new JSONParser();
			Object obj = JSONValue.parse(dataString);
			JSONObject searchObject = (JSONObject) parser.parse(obj.toString());
			String groupId = (String) searchObject.get(Constants.GROUP_ID);
			String screenType = (String) searchObject.get(Constants.PERMISSION_SCREEN_TYPE);

			List<String> screenIds = screenRepository.findAll(Constants.PERMISSION_SCREEN_ID);
			List<GroupScreen> grpScrenList = groupScreenRepository.findAll(groupId);
			List<String> scrList = new ArrayList<String>();
			for (GroupScreen groupScreen : grpScrenList) {
				JSONObject scrObj = new JSONObject();
				String screenId = groupScreen.getId().getScreenId();
				Optional<Screen> screenEntity = screenRepository
						.findOne(SecurityPermissionSpec.getScreen(screenId, screenType));
				if (screenEntity.isPresent()) {
					Screen screen = screenEntity.get();
					String addFlg = groupScreen.getAddFl();
					String updateFlag = groupScreen.getUpdFl();
					String disFlag = groupScreen.getDspFl();
					String delFlag = groupScreen.getDelFl();
					String verFlag = groupScreen.getVerFl();
					if (!StringUtils.isEmpty(screen)) {
						String categoryName = screen.getCategoryNanme();
						scrList.add(screenId);
						scrObj.put(Constants.PERMISSION_SCREEN_ID, screenId);
						scrObj.put(Constants.PERMISSION_SCREEN_NAME, screen.getScreenName());
						scrObj.put(Constants.PERMISSION_ADD_FLAG, addFlg);
						scrObj.put(Constants.PERMISSION_UPDATE_FLAG, updateFlag);
						scrObj.put(Constants.PERMISSION_DISPLAY_FLAG, disFlag);
						scrObj.put(Constants.PERMISSION_DELETE_FLAG, delFlag);
						scrObj.put(Constants.PERMISSION_VERIFY_FLAG, verFlag);
						scrObj.put(Constants.PERMISSION_CATEGORY_NAME, categoryName);
						resArray.add(scrObj);
					}
				}
			}
			for (String screen : screenIds) {
				Optional<Screen> screenEntity = screenRepository
						.findOne(SecurityPermissionSpec.getScreen(screen, screenType));

				if (screenEntity.isPresent()) {
					Screen screen1 = screenEntity.get();
					if (!StringUtils.isEmpty(screen1)) {
						String screenName = screen1.getScreenName();
						String categoryName = screen1.getCategoryNanme();
						JSONObject screenObj = new JSONObject();
						if (scrList.size() > 0) {
							if (!scrList.contains(screen)) {
								screenObj.put(Constants.PERMISSION_SCREEN_ID, screen);
								screenObj.put(Constants.PERMISSION_SCREEN_NAME, screenName);
								screenObj.put(Constants.PERMISSION_ADD_FLAG, Constants.PERMISSION_NO);
								screenObj.put(Constants.PERMISSION_UPDATE_FLAG, Constants.PERMISSION_NO);
								screenObj.put(Constants.PERMISSION_DISPLAY_FLAG, Constants.PERMISSION_NO);
								screenObj.put(Constants.PERMISSION_DELETE_FLAG, Constants.PERMISSION_NO);
								screenObj.put(Constants.PERMISSION_VERIFY_FLAG, Constants.PERMISSION_NO);
								screenObj.put(Constants.PERMISSION_CATEGORY_NAME, categoryName);
								resArray.add(screenObj);
							}
						} else {
							screenObj.put(Constants.PERMISSION_SCREEN_ID, screen);
							screenObj.put(Constants.PERMISSION_SCREEN_NAME, screenName);
							screenObj.put(Constants.PERMISSION_ADD_FLAG, Constants.PERMISSION_NO);
							screenObj.put(Constants.PERMISSION_UPDATE_FLAG, Constants.PERMISSION_NO);
							screenObj.put(Constants.PERMISSION_DISPLAY_FLAG, Constants.PERMISSION_NO);
							screenObj.put(Constants.PERMISSION_DELETE_FLAG, Constants.PERMISSION_NO);
							screenObj.put(Constants.PERMISSION_VERIFY_FLAG, Constants.PERMISSION_NO);
							screenObj.put(Constants.PERMISSION_CATEGORY_NAME, categoryName);
							resArray.add(screenObj);
						}
					}
				}

			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return resArray;
	}

	@Override
	public ServiceResponse saveGroupScreenPermission(String reqObj) {
		JSONObject res = new JSONObject();
		JSONParser parser = new JSONParser();
		List<GroupScreen> groupScreenArr = new ArrayList<GroupScreen>();
		String alphanumerSpace = Constants.ALPHA_NUMER_SPACES;
		String alphanumers = Constants.ALPHA_NUMERS;
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(reqObj);
			String groupId = (String) jsonObject.get(Constants.GROUP_ID);
			if (!groupId.matches(alphanumers)) {
//				res.put(Constants.STATUS, false);
//				res.put(Constants.MESSAGE, Constants.PERMISSION_GROUP_ID_REGEX_MSG);
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
						"user.permission.psh.VAL0002", null, LocaleContextHolder.getLocale()), null);
			} else {
				JSONArray screenArr = (JSONArray) jsonObject.get(Constants.PERMISSION_GROUP_ARRAY);
				for (int i = 0; i < screenArr.size(); i++) {
					String add = Constants.PERMISSION_NO;
					String del = Constants.PERMISSION_NO;
					String dis = Constants.PERMISSION_NO;
					String upd = Constants.PERMISSION_NO;
					String ver = Constants.PERMISSION_NO;
					GroupScreen eftGsc = new GroupScreen();
					JSONObject obj = (JSONObject) screenArr.get(i);
					String screenId = (String) obj.get(Constants.PERMISSION_SCREEN_ID);
					boolean addFlag = (boolean) obj.get(Constants.PERMISSION_ADD_FLAG);
					if (addFlag)
						add = Constants.PERMISSION_YES;
					boolean delFlag = (boolean) obj.get(Constants.PERMISSION_DELETE_FLAG);
					if (delFlag)
						del = Constants.PERMISSION_YES;
					boolean disFlag = (boolean) obj.get(Constants.PERMISSION_DISPLAY_FLAG);
					if (disFlag)
						dis = Constants.PERMISSION_YES;
					boolean updFlag = (boolean) obj.get(Constants.PERMISSION_UPDATE_FLAG);
					if (updFlag)
						upd = Constants.PERMISSION_YES;
					boolean verFlag = (boolean) obj.get(Constants.PERMISSION_VERIFY_FLAG);
					if (verFlag)
						ver = Constants.PERMISSION_YES;
					GroupScreenPK gPk = new GroupScreenPK();
					gPk.setGroupId(groupId);
					gPk.setScreenId(screenId);
					eftGsc.setId(gPk);
					eftGsc.setSysDate(new Date());
					eftGsc.setAddFl(add);
					eftGsc.setDelFl(del);
					eftGsc.setDspFl(dis);
					eftGsc.setUpdFl(upd);
					eftGsc.setVerFl(ver);
					groupScreenArr.add(eftGsc);
				}
				for (GroupScreen grpScr : groupScreenArr) {
					groupScreenRepository.saveAndFlush(grpScr);
				}

//				res.put(Constants.STATUS, true);
//				res.put(Constants.MESSAGE, Constants.PERMISSION_SCREEN_CREATED);
				return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, messageSource.getMessage(
						"user.permission.psh.VAL0001", null, LocaleContextHolder.getLocale()), null);
			}
		} catch(Exception e) {
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
					"user.permission.psh.VAL0003", null, LocaleContextHolder.getLocale()), null);
		}
//		catch (DataAccessException e) {
//			logger.error(e.getMessage(), e);
//		} catch (ParseException e) {
//			logger.error(e.getMessage(), e);
//		}
		//return res;
	}

	@Override
	public ServiceResponse createGroup(Group groupDetails, Principal principal , String screenId) {
		JSONObject res = new JSONObject();
		String groupId = groupDetails.getGroupId();
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:sss");
		Date date = new Date();
		String newAuditLogData = null;
		String oldAuditLogData = null;
		try {
			String groupName = groupDetails.getGroupName();
			String homeScreenUrl = groupDetails.getHomeScreenUrl();
			Optional<Group> groupEntity = groupRepository.findById(groupId);
			if (!groupEntity.isPresent()) {

				Group group = new Group();
				group.setGroupId(groupDetails.getGroupId());
				group.setGroupName(groupDetails.getGroupName());
				group.setHomeScreenUrl(groupDetails.getHomeScreenUrl());
				group.setStatus(Constants.PROCESSD);
				group.setCreatedBy(getUserName());
				group.setSysDate(date);
				group.setProTime(date);
				String notificationId = UUID.randomUUID().toString();
				group.setNotificationId(notificationId);
				group.setGroupType(1);
				GroupDetails groupDetailsNew = new GroupDetails();
				BeanUtils.copyProperties(group, groupDetailsNew);
				groupDetailsNew.setCreatedOn(format.format(date));
				group = groupRepository.save(group);
				notificationService.sendNotificationSecGroup(group, "C", "V");
				newAuditLogData = convertToJson(group);
				if (group != null) {
					auditLogUtil.creatAudit(oldAuditLogData, newAuditLogData, AuditFuctions.CREATE, Constants.GROUP_SCREEN_ID,
							Constants.MESSAGE_STATUS.SUCCESS,
							messageSource.getMessage("user.group.psh.VAL0001", null, Locale.getDefault()),principal);
					
					return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, messageSource.getMessage(
							"user.group.psh.VAL0001", null, LocaleContextHolder.getLocale()), null);
				} else {
					return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
							"user.group.psh.VAL0002", null, LocaleContextHolder.getLocale()), null);
				}
//				boolean status = (boolean) res.get(Constants.STATUS);
				/*
				 * if (status) { AuditHistory auditHistory = new AuditHistory(); AuditHistoryPK
				 * auditHistoryPK = new AuditHistoryPK(); auditHistoryPK.setUserId(createdBy);
				 * auditHistoryPK.setClientId(Constants.PERMISSION_DEFAULT_CLIENT_ID);
				 * auditHistoryPK.setDateTime(date); auditHistoryPK.setScreenId(screenId);
				 * auditHistory.setId(auditHistoryPK);
				 * auditHistory.setFreeText(Constants.GROUP_CREATED);
				 * auditHistory.setFunctionFlag(Constants.PERMISSION_FUNCTION_FLAG);
				 * auditHistory.setCurrentData(groupDetailsNew.toString());
				 * auditHistory.setStatus(Constants.PROCESSD);
				 * auditHistoryRepository.save(auditHistory);
				 * 
				 * }
				 */
//				res.put(Constants.MESSAGE, Constants.GROUP_CREATED);
			} else {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
						"user.group.psh.VAL0003", null, LocaleContextHolder.getLocale()), null);
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
					"user.group.psh.VAL0002", null, LocaleContextHolder.getLocale()), null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
					"user.group.psh.VAL0002", null, LocaleContextHolder.getLocale()), null);
		}
	}
	
	String convertToJson(Group group) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(group);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return "";
	}

	@Override
	public List<String> getGroupListForNotification(String permissionType, String screenId) {
		if ("V".equalsIgnoreCase(permissionType)) {
			return groupScreenRepository.findVerifiableGroups(screenId);
		} else if ("P".equalsIgnoreCase(permissionType)) {
			return groupScreenRepository.findProcessorGroups(screenId);
		} else if ("D".equalsIgnoreCase(permissionType)) {
			return groupScreenRepository.findDeleteGroups(screenId);
		}
		return new ArrayList<String>();
	}

	@Override
	public GroupScreen getButtonPermission(String groupId, String screenId) throws RecordNotFoundException {
		try {
			Optional<GroupScreen> permission = groupScreenRepository.findById(new GroupScreenPK(groupId, screenId));
			if (permission.isPresent()) {
				return permission.get();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		throw new RecordNotFoundException("groupscreen.psh.VAL0001");
	}
	
	private String getUserName() {
		Jwt p = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> claims = p.getClaims();
		return (String) claims.get("preferred_username");
	}

}

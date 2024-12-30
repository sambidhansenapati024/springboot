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
** Program description  : SecurityGroupServiceImpl
** Version No           : 1.0.0
** Author               : Yadhu
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.service;

import java.security.Principal;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.alfaris.ipsh.security.client.AuditLogUtil;
import com.alfaris.ipsh.security.dto.ServiceResponse;
import com.alfaris.ipsh.security.entity.Group;
import com.alfaris.ipsh.security.exception.RecordNotFoundException;
import com.alfaris.ipsh.security.exception.RecordUpdateException;
import com.alfaris.ipsh.security.repository.GroupRepository;
import com.alfaris.ipsh.security.repository.specification.SecurityGroupSpec;
import com.alfaris.ipsh.security.util.AuditFuctions;
import com.alfaris.ipsh.security.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SecurityGroupServiceImpl implements SecurityGroupService {
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	AuditLogUtil auditLogUtil;
	
	@Autowired
	NotificationService notificationService;
	
	private static Logger logger = LogManager.getLogger(SecurityGroupServiceImpl.class);

	@Override
	public JSONObject searchGroupByLimit(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Page<Group> usersList = groupRepository.findAll(SecurityGroupSpec.getGroupByNameSpec(searchParam), pageable);
			JSONArray array = new JSONArray();
			for (Group secGroupEntity : usersList) {
				JSONObject obj = new JSONObject();
				
				obj.put(Constants.GROUP_ID, secGroupEntity.getGroupId());
				obj.put(Constants.GROUP_NAME, secGroupEntity.getGroupName());
				obj.put(Constants.GROUP_HOME_SCREEN, secGroupEntity.getHomeScreenUrl());
				obj.put(Constants.GROUP_TYPE, secGroupEntity.getGroupType());
				obj.put(Constants.GROUP_STATUS, secGroupEntity.getStatus());
				obj.put(Constants.GROUP_HOME_URL, secGroupEntity.getHomeScreenUrl());
				array.add(obj);
			}
			result.put(Constants.AA_DATA, array);
			result.put(Constants.TOTAL_DISPLAY_RECORD, groupRepository.findAll().size());
			result.put(Constants.TOTAL_RECORD, groupRepository.findAll().size());
		} catch (Exception e) {
			result.put(Constants.AA_DATA, new JSONArray());
			result.put(Constants.TOTAL_DISPLAY_RECORD, 0);
			result.put(Constants.TOTAL_RECORD, 0);
			logger.error("Error : " + e.getMessage(), e);
		}

		return result;
	}

	/*
	 * @Override public JSONObject createGroup(Group group) { JSONObject result =
	 * new JSONObject(); boolean status = false; String message = null; try {
	 * Optional<Group> res = groupRepository.findById(group.getGroupId()); if
	 * (res.isPresent()) { result.put(Constants.STATUS, status);
	 * result.put(Constants.MESSAGE, Constants.GROUP_EXIST); return result; } group
	 * = groupRepository.save(group); if (group != null) { status = true; message =
	 * Constants.GROUP_CREATED; } else { message = Constants.GROUP_CREATION_FAILED;
	 * } } catch (Exception e) { logger.error("Error : " + e.getMessage(), e);
	 * result.put(Constants.STATUS, status); result.put(Constants.MESSAGE,
	 * Constants.GROUP_CREATION_FAILED); return result; }
	 * result.put(Constants.STATUS, status); result.put(Constants.MESSAGE, message);
	 * return result; }
	 */

	@Override
	public ServiceResponse updateGroup(Group group, Principal principal) throws RecordUpdateException,RecordNotFoundException{
		JSONObject result = new JSONObject();
		boolean status = false;
		String message = null;
		String newAuditLogData = null;
		String oldAuditLogData = null;
		try {
			Optional<Group> data = groupRepository.findById(group.getGroupId());
			if (!data.isPresent()) {
				throw new RecordNotFoundException("user.group.psh.VAL0004");
			}
			Group newEntity = data.get();
			oldAuditLogData = convertToJson(newEntity);
			newEntity.setGroupId(group.getGroupId());
			newEntity.setGroupName(group.getGroupName());
			newEntity.setHomeScreenUrl(group.getHomeScreenUrl());
			newEntity.setGroupType(1);
			newEntity.setModifiedBy(getUserName());
			newEntity.setVerTime(new Date());
			String notificationId = UUID.randomUUID().toString();
			newEntity.setNotificationId(notificationId);
			newEntity.setStatus(Constants.PROCESSD);
			newEntity = groupRepository.save(newEntity);
			if (newEntity != null) {
				newAuditLogData = convertToJson(newEntity);
				notificationService.sendNotificationSecGroup(newEntity, "M", "V");
				auditLogUtil.creatAudit(oldAuditLogData, newAuditLogData, AuditFuctions.MODIFY, Constants.GROUP_SCREEN_ID,
						Constants.MESSAGE_STATUS.SUCCESS,
						messageSource.getMessage("user.group.psh.VAL0005", null, Locale.getDefault()), principal);
				
				return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, messageSource.getMessage(
						"user.group.psh.VAL0005", null, LocaleContextHolder.getLocale()), null);
			} else {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
						"user.group.psh.VAL0006", null, LocaleContextHolder.getLocale()), null);
			}
		}catch (RecordNotFoundException e) {
			throw e;
		}
		catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
					"user.group.psh.VAL0006", null, LocaleContextHolder.getLocale()), null);

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
	public ServiceResponse deleteGroup(String groupId,Principal principal) throws RecordNotFoundException{
		JSONObject result = new JSONObject();
		boolean status = false;
		String message = null;
		String newAuditLogData = null;
		String oldAuditLogData = null;
		try {
			Optional<Group> data = groupRepository.findById(groupId);
			if (!data.isPresent()) {
				throw new RecordNotFoundException("user.group.psh.VAL0004");
			}
			Group newEntity = data.get();
			newEntity.setStatus(Constants.DELETE);
			newEntity.setModifiedBy(getUserName());
			newEntity.setVerTime(new Date());
			newEntity = groupRepository.save(newEntity);
			if (newEntity != null) {
				newAuditLogData = convertToJson(newEntity);
				
				auditLogUtil.creatAudit(oldAuditLogData, newAuditLogData, AuditFuctions.DELETE, Constants.GROUP_SCREEN_ID,
						Constants.MESSAGE_STATUS.SUCCESS,
						messageSource.getMessage("user.group.psh.VAL0007", null, Locale.getDefault()), principal);
				
				return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, messageSource.getMessage(
						"user.group.psh.VAL0007", null, LocaleContextHolder.getLocale()), null);
			} else {
				return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, messageSource.getMessage(
						"user.group.psh.VAL0008", null, LocaleContextHolder.getLocale()), null);
			}
		}catch (RecordNotFoundException e) {
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
					"user.group.psh.VAL0004", null, LocaleContextHolder.getLocale()), null);
		} catch (Exception e) {
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, messageSource.getMessage(
					"user.group.psh.VAL0008", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@Override
	public ServiceResponse verifyGroup(String groupId, Principal principle) throws RecordNotFoundException{
		JSONObject result = new JSONObject();
		boolean status = false;
		String message = null;
		String newAuditLogData = null;
		String oldAuditLogData = null;
		try {
			Optional<Group> data = groupRepository.findById(groupId);
			if (!data.isPresent()) {

				throw new RecordNotFoundException("user.group.psh.VAL0004");
			}
			Group newEntity = data.get();
			if (newEntity.getStatus().equalsIgnoreCase(Constants.MESSAGE_STATUS.DELETE)) {
				if (getUserName().equals(newEntity.getCreatedBy())) {

					return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
							"user.group.psh.VAL0009", null, LocaleContextHolder.getLocale()), null);
				} else {
					//groupRepository.delete(newEntity);
					newEntity.setStatus(Constants.MESSAGE_STATUS.DELETED);
					newEntity.setModifiedBy(getUserName());
					newEntity.setVerTime(new Date());
					groupRepository.save(newEntity);
					newAuditLogData = convertToJson(newEntity);
					
					auditLogUtil.creatAudit(oldAuditLogData, newAuditLogData, AuditFuctions.DELETE, Constants.GROUP_SCREEN_ID,
							Constants.MESSAGE_STATUS.SUCCESS,
							messageSource.getMessage("user.group.psh.VAL0007", null, Locale.getDefault()), principle);
					
					return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, messageSource.getMessage(
							"user.group.psh.VAL0007", null, LocaleContextHolder.getLocale()), null);
				}
			} else{
				if (getUserName().equals(newEntity.getCreatedBy())) {

					return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
							"user.group.psh.VAL0011", null, LocaleContextHolder.getLocale()), null);
				}

				newEntity.setStatus(Constants.VERIFIED);
				newEntity.setModifiedBy(getUserName());
				newEntity.setVerTime(new Date());
				newEntity = groupRepository.save(newEntity);
				if (newEntity != null) {
					newAuditLogData = convertToJson(newEntity);
					auditLogUtil.creatAudit(oldAuditLogData, newAuditLogData, AuditFuctions.VERIFY, Constants.GROUP_SCREEN_ID,
							Constants.MESSAGE_STATUS.SUCCESS,
							messageSource.getMessage("user.group.psh.VAL0010", null, Locale.getDefault()), principle);
					
					return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, messageSource.getMessage(
							"user.group.psh.VAL0010", null, LocaleContextHolder.getLocale()), null);
				} else {
					return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
							"user.group.psh.VAL0012", null, LocaleContextHolder.getLocale()), null);
				}
			}
		}catch (RecordNotFoundException e) {
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
					"user.group.psh.VAL0004", null, LocaleContextHolder.getLocale()), null);
		} 
		catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
					"user.group.psh.VAL0012", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@Override
	public ServiceResponse reActivateGroup(String groupId) throws RecordNotFoundException{
		JSONObject result = new JSONObject();
		boolean status = false;
		String message = null;
		try {
			Optional<Group> data = groupRepository.findById(groupId);
			if (!data.isPresent()) {
				throw new RecordNotFoundException("user.group.psh.VAL0004");
			}
			Group newEntity = data.get();
			if(newEntity.getStatus().equalsIgnoreCase(Constants.DELETED)) {
			newEntity.setStatus(Constants.PROCESSD);
			newEntity = groupRepository.save(newEntity);
			if (newEntity != null) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, messageSource.getMessage(
						"user.group.psh.VAL0013", null, LocaleContextHolder.getLocale()), null);
			} else {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
						"user.group.psh.VAL0014", null, LocaleContextHolder.getLocale()), null);
			}
			}else if(newEntity.getStatus().equalsIgnoreCase(Constants.MESSAGE_STATUS.DELETE)) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
						"user.group.psh.VAL0016", null, LocaleContextHolder.getLocale()), null);
			}
			else {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
						"user.group.psh.VAL0015", null, LocaleContextHolder.getLocale()), null);
			}
		}catch (RecordNotFoundException e) {
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
					"user.group.psh.VAL0004", null, LocaleContextHolder.getLocale()), null);
		} 
		catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource.getMessage(
					"user.group.psh.VAL0014", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@Override
	public Group getDataById(String groupId) throws RecordNotFoundException{
		Optional<Group> groupData = groupRepository.findById(groupId);
		if (groupData.isPresent()) {
			return groupData.get();
		} else {
			throw new RecordNotFoundException("user.group.psh.VAL0017");
		}
	}

	@Override
	public Group getByNotificationId(String notificationId) throws RecordNotFoundException{
		List<Group> resultList = groupRepository.findByNotificationId(notificationId);
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		} else {
			throw new RecordNotFoundException("user.group.psh.VAL0004");
		}
	}
	
	private String getUserName() {
		Jwt p = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> claims = p.getClaims();
		return (String) claims.get("preferred_username");
	}
}

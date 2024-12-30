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
** Program description  : SecurityPermissionService
** Version No           : 1.0.0
** Author               : Adarsh
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.service;

import java.security.Principal;
import java.util.List;

import jakarta.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.alfaris.ipsh.security.dto.GroupDetails;
import com.alfaris.ipsh.security.dto.ServiceResponse;
import com.alfaris.ipsh.security.entity.Group;
import com.alfaris.ipsh.security.entity.GroupScreen;
import com.alfaris.ipsh.security.exception.RecordNotFoundException;

public interface SecurityPermissionService {

	JSONArray getGrpIdName(String userId);

	JSONArray getGrpScrPermission(String dataString) throws ParseException;

	ServiceResponse saveGroupScreenPermission(String reqObj);

	ServiceResponse createGroup(@Valid Group groupDetails, Principal principal, String screenId);

	List<String> getGroupListForNotification(String permissionType, String screenId);
	
	GroupScreen getButtonPermission(String groupId, String screenId) throws RecordNotFoundException;

}

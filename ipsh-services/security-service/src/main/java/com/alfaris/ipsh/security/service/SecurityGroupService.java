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
** Program description  : SecurityGroupService
** Version No           : 1.0.0
** Author               : Yadhu
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.service;

import java.security.Principal;

import org.json.simple.JSONObject;

import com.alfaris.ipsh.security.dto.ServiceResponse;
import com.alfaris.ipsh.security.entity.Group;
import com.alfaris.ipsh.security.exception.RecordNotFoundException;
import com.alfaris.ipsh.security.exception.RecordUpdateException;

public interface SecurityGroupService {

	JSONObject searchGroupByLimit(String searchParam, int parseInt, int parseInt2);

//	JSONObject createGroup(Group group);

	ServiceResponse updateGroup(Group group, Principal principal) throws RecordUpdateException, RecordNotFoundException;

	ServiceResponse deleteGroup(String groupId, Principal principal) throws RecordNotFoundException;

	ServiceResponse verifyGroup(String groupId, Principal principle) throws RecordNotFoundException;

	ServiceResponse reActivateGroup(String groupId) throws RecordNotFoundException;

	Group getDataById(String groupId) throws RecordNotFoundException;

	Group getByNotificationId(String notificationId) throws RecordNotFoundException;

}

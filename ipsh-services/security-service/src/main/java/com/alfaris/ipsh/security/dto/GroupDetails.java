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
** Program description  : GroupDetails
** Version No           : 1.0.0
** Author               : Yadhu
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.dto;

import java.io.Serializable;

public class GroupDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  groupId;
    private String  groupName;
    private String  status;
    private String homeScreenUrl;
    private Integer groupType;
    private String createdBy;
    private String createdOn;
    private String modifiedBy;
    private String modifiedOn;
    
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public Integer getGroupType() {
		return groupType;
	}
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHomeScreenUrl() {
		return homeScreenUrl;
	}
	public void setHomeScreenUrl(String homeScreenUrl) {
		this.homeScreenUrl = homeScreenUrl;
	}
	@Override
	public String toString() {
		return  "{\"groupId\":\"" + groupId + "\", \"groupName\":\"" + groupName
		+ "\", \"status\":\"" + status + "\", \"homeScreenUrl\":\"" + homeScreenUrl
		+ "\", \"createdBy\":\"" + createdBy + "\", \"createdOn\":\"" + createdOn
		+ "\", \"modifiedBy\":\"" + modifiedBy + "\", \"modifiedOn\":\"" + modifiedOn
		+ "\", \"groupType\":\"" + groupType + "\"}";
	}
    
}

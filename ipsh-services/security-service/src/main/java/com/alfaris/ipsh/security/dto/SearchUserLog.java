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
** Program description  : SearchUserLog
** Version No           : 1.0.0
** Author               : Harichand
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.dto;

public class SearchUserLog {
	
	private String userId;
	private String logStatus;
	private String ipAddress;
	private String logTime;

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLogStatus() {
		return logStatus;
	}
	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	@Override
	public String toString() {
		return "SearchUserLog [userId=" + userId + ", logStatus=" + logStatus + ", ipAddress=" + ipAddress
				+ ", logTime=" + logTime + "]";
	}
	public SearchUserLog(String userId, String logStatus, String ipAddress, String logTime) {
		super();
		this.userId = userId;
		this.logStatus = logStatus;
		this.ipAddress = ipAddress;
		this.logTime = logTime;
	}
	public SearchUserLog() {
		super();
	}

}

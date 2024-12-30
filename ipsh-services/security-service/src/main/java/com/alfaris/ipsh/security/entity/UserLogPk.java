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
** Program description  : UserLogPk
** Version No           : 1.0.0
** Author               : Harichand
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;

@Embeddable
public class UserLogPk implements Serializable{


	private static final long serialVersionUID = 1L;

	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "LOG_TIME")
	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    Date logTime;

	public UserLogPk(String userId, Date logTime) {
		super();
		this.userId = userId;
		this.logTime = logTime;
	}
	
	public UserLogPk() {
		 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logTime == null) ? 0 : logTime.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserLogPk other = (UserLogPk) obj;
		if (logTime == null) {
			if (other.logTime != null)
				return false;
		} else if (!logTime.equals(other.logTime))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
}

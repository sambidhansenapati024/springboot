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
** Program description  : GroupScreenPK
** Version No           : 1.0.0
** Author               : Adarsh
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.liquidity.dto;

import java.io.Serializable;

/**
 * The primary key class for the EFTSECGSC database table.
 * 
 */

public class GroupScreenPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String groupId;

	private String screenId;

	public GroupScreenPK() {
	}
	
	
	public GroupScreenPK(String groupId, String screenId) {
		super();
		this.groupId = groupId;
		this.screenId = screenId;
	}


	public String getGroupId() {
		return this.groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getScreenId() {
		return this.screenId;
	}
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GroupScreenPK)) {
			return false;
		}
		GroupScreenPK castOther = (GroupScreenPK)other;
		return 
			this.groupId.equals(castOther.groupId)
			&& this.screenId.equals(castOther.screenId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.groupId.hashCode();
		hash = hash * prime + this.screenId.hashCode();
		
		return hash;
	}
}
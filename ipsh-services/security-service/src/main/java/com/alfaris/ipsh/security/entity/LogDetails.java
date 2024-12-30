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
** Program description  : LogDetails
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
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;

@Entity
@Table(name = "EFTSECADT", schema = "IPSH")
public class LogDetails implements Serializable{
    
    @EmbeddedId
    UserLogPk pk;
  
    @Column(name = "LOGIN_STS")
    String logStatus;
    @Column(name = "LOGIN_TYPE")
    String loginType;
    @Column(name = "REASON")
    String reason;
    @Column(name = "IPID")
    String ipAddress;
    @Column(name = "SYS_TIME")
    @Temporal(jakarta.persistence.TemporalType.DATE)
    Date sysDate;
    
    public UserLogPk getPk() {
		return pk;
	}
	public void setPk(UserLogPk pk) {
		this.pk = pk;
	}
	
	public String getLogStatus() {
		return logStatus;
	}
	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Date getSysDate() {
		return sysDate;
	}
	public void setSysDate(Date sysDate) {
		this.sysDate = sysDate;
	}
	
}

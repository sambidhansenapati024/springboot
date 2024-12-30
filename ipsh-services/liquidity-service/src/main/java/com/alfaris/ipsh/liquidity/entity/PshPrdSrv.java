/*********************************************************************************************
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
** Program description  : PshPrdSrv
** Version No           : 1.0.0
** Author               : Athul Rajesh
** Date Created         : 21-may-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
**********************************************************************************************/

package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="PSH_PRD_SRV",schema = "ipsh")
public class PshPrdSrv implements Serializable{
	
	@EmbeddedId
	private PshPrdSrvPK id;
	
	@Column(name="SERVICE_NAME_L1")
	private String serviceNameL1;
	
	@Column(name="SERVICE_NAME_L2")
	private String serviceNameL2;
	
	@Column(name="MESSAGE_FORMAT")
	private String messageFormat;
	
	@Column(name="SB_FLAG")
	private String sbFlag;
	
	@Column(name="MESSAGE_TYPE")
	private String messageType;
	
	@Column(name="PRO_ID")
	private String proId;
	
	@Column(name=" VER_ID1")
	private String verId1;
	
	@Column(name=" VER_ID2")
	private String verId2;
	
	@Column(name=" VER_ID3")
	private String verId3;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PRO_TIME")
	private Date proTime;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VER1_TIME")
	private Date ver1Time;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VER2_TIME")
	private Date ver2Time;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VER3_TIME")
	private Date ver3Time;

	public PshPrdSrvPK getId() {
		return id;
	}

	public void setId(PshPrdSrvPK id) {
		this.id = id;
	}

	public String getServiceNameL1() {
		return serviceNameL1;
	}

	public void setServiceNameL1(String serviceNameL1) {
		this.serviceNameL1 = serviceNameL1;
	}

	public String getServiceNameL2() {
		return serviceNameL2;
	}

	public void setServiceNameL2(String serviceNameL2) {
		this.serviceNameL2 = serviceNameL2;
	}

	public String getMessageFormat() {
		return messageFormat;
	}

	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	public String getSbFlag() {
		return sbFlag;
	}

	public void setSbFlag(String sbFlag) {
		this.sbFlag = sbFlag;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getVerId1() {
		return verId1;
	}

	public void setVerId1(String verId1) {
		this.verId1 = verId1;
	}

	public String getVerId2() {
		return verId2;
	}

	public void setVerId2(String verId2) {
		this.verId2 = verId2;
	}

	public String getVerId3() {
		return verId3;
	}

	public void setVerId3(String verId3) {
		this.verId3 = verId3;
	}

	public Date getProTime() {
		return proTime;
	}

	public void setProTime(Date proTime) {
		this.proTime = proTime;
	}

	public Date getVer1Time() {
		return ver1Time;
	}

	public void setVer1Time(Date ver1Time) {
		this.ver1Time = ver1Time;
	}

	public Date getVer2Time() {
		return ver2Time;
	}

	public void setVer2Time(Date ver2Time) {
		this.ver2Time = ver2Time;
	}

	public Date getVer3Time() {
		return ver3Time;
	}

	public void setVer3Time(Date ver3Time) {
		this.ver3Time = ver3Time;
	}
	
	

}

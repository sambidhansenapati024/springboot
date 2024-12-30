/**************************
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
** Program description  : PshUsrDep
** Version No           : 1.0.0
** Author               : Sabari S 
** Date Created         : 17-May-2021
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
***************************/

package com.alfaris.ipsh.security.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;


/**
 * The persistent class for the UPS_PAY_HDR database table.
 * 
 */
@Entity
@Table(name = "PSH_USR_DEP",schema = "IPSH")
@NamedQuery(name = "PshUsrDep.findAll", query = "SELECT u FROM PshUsrDep u")
public class PshUsrDep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Valid
	@EmbeddedId
	private PshUsrDepPK id;

	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;

	@Column(name = "PRO_ID")
	private String proId;

	@Column(name = "VER_ID1")
	private String verId1;

	@Column(name = "VER_ID2")
	private String verId2;

	@Column(name = "VER_ID3")
	private String verId3;

	@Temporal(TemporalType.DATE)
	@Column(name = "PRO_TIME")
	private Date proTime;

	@Temporal(TemporalType.DATE)
	@Column(name = "VER1_TIME")
	private Date ver1Time;

	@Temporal(TemporalType.DATE)
	@Column(name = "VER2_TIME")
	private Date ver2Time;

	@Temporal(TemporalType.DATE)
	@Column(name = "VER3_TIME")
	private Date ver3Time;

	private String status;

	@Column(name = "STATUS_MESSAGE")
	private String statusMessage;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_TIME")
	private Date modifiedTime;

	
	
	@Column(name = "ALTERNATIVE_ID")
	private String alternativeId;

	@Column(name = "NOTIFICATION_ID")
	private String notificationId;

	@Column(name = "SARIE_DEPARTMENT")
	private String sarieDepartment;
	
	
	
	@Column(name = "IPS_CODE")
	private String ipsCode;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public PshUsrDepPK getId() {
		return id;
	}

	public void setId(PshUsrDepPK id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getAlternativeId() {
		return alternativeId;
	}

	public void setAlternativeId(String alternativeId) {
		this.alternativeId = alternativeId;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getSarieDepartment() {
		return sarieDepartment;
	}

	public void setSarieDepartment(String sarieDepartment) {
		this.sarieDepartment = sarieDepartment;
	}

	public String getIpsCode() {
		return ipsCode;
	}

	public void setIpsCode(String ipsCode) {
		this.ipsCode = ipsCode;
	}

	
	
	

}
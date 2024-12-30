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
** Program description  : GroupScreen
** Version No           : 1.0.0
** Author               : Adarsh
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.kycservice.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the EFTSECGSC database table.
 * 
 */
@Entity
@Table(name = "EFTSECGSC", schema = "IPSH")
//@NamedQuery(name="GroupScreen.findAll", query="SELECT e FROM GroupScreen e")
public class GroupScreen implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GroupScreenPK id;

	@Column(name="ADD_FL")
	private String addFl;

	@Column(name="DEL_FL")
	private String delFl;

	@Column(name="DSP_FL")
	private String dspFl;

	@Column(name="PRO_ID")
	private String proId;

	@Column(name="PRO_TIME")
	private Timestamp proTime;

	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name="SYS_DATE")
	private Date sysDate;

	@Column(name="UPD_FL")
	private String updFl;

	@Column(name="VER_FL")
	private String verFl;

	@Column(name="VER_ID")
	private String verId;

	@Column(name="VER_TIME")
	private Timestamp verTime;

	public GroupScreen() {
	}

	public GroupScreenPK getId() {
		return this.id;
	}

	public void setId(GroupScreenPK id) {
		this.id = id;
	}

	public String getAddFl() {
		return this.addFl;
	}

	public void setAddFl(String addFl) {
		this.addFl = addFl;
	}

	public String getDelFl() {
		return this.delFl;
	}

	public void setDelFl(String delFl) {
		this.delFl = delFl;
	}

	public String getDspFl() {
		return this.dspFl;
	}

	public void setDspFl(String dspFl) {
		this.dspFl = dspFl;
	}

	public String getProId() {
		return this.proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public Timestamp getProTime() {
		return this.proTime;
	}

	public void setProTime(Timestamp proTime) {
		this.proTime = proTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSysDate() {
		return this.sysDate;
	}

	public void setSysDate(Date sysDate) {
		this.sysDate = sysDate;
	}

	public String getUpdFl() {
		return this.updFl;
	}

	public void setUpdFl(String updFl) {
		this.updFl = updFl;
	}

	public String getVerFl() {
		return this.verFl;
	}

	public void setVerFl(String verFl) {
		this.verFl = verFl;
	}

	public String getVerId() {
		return this.verId;
	}

	public void setVerId(String verId) {
		this.verId = verId;
	}

	public Timestamp getVerTime() {
		return this.verTime;
	}

	public void setVerTime(Timestamp verTime) {
		this.verTime = verTime;
	}

}
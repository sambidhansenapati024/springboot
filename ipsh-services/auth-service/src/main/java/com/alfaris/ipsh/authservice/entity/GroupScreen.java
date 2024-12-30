package com.alfaris.ipsh.authservice.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
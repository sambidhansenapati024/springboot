package com.alfaris.ipsh.authservice.entity;

import java.io.Serializable;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * The persistent class for the EFTSECADT database table.
 * 
 */
@Entity
@Table(name="EFTSECADT", schema = "IPSH")
//@NamedQuery(name="Eftsecadt.findAll", query="SELECT e FROM Eftsecadt e")
public class Eftsecadt implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EftsecadtPK id;

	private String ipid;

	@Column(name="LOGIN_STS")
	private String loginSts;

	@Column(name="LOGIN_TYPE")
	private String loginType;

	private String reason;

	@Column(name="SYS_TIME")
	private Timestamp sysTime;

	public Eftsecadt() {
	}

	public EftsecadtPK getId() {
		return this.id;
	}

	public void setId(EftsecadtPK id) {
		this.id = id;
	}

	public String getIpid() {
		return this.ipid;
	}

	public void setIpid(String ipid) {
		this.ipid = ipid;
	}

	public String getLoginSts() {
		return this.loginSts;
	}

	public void setLoginSts(String loginSts) {
		this.loginSts = loginSts;
	}

	public String getLoginType() {
		return this.loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Timestamp getSysTime() {
		return this.sysTime;
	}

	public void setSysTime(Timestamp sysTime) {
		this.sysTime = sysTime;
	}

}
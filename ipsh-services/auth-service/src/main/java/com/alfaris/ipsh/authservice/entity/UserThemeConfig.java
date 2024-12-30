package com.alfaris.ipsh.authservice.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * The persistent class for the UPS_USR_CONFIG database table.
 * 
 */
@Entity
@Table(name = "UPS_USR_CONFIG", schema = "IPSH")
//@NamedQuery(name = "UserThemeConfig.findAll", query = "SELECT u FROM UserThemeConfig u")
public class UserThemeConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Lob
	private byte[] config;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	public UserThemeConfig() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public byte[] getConfig() {
		return this.config;
	}

	public void setConfig(byte[] config) {
		this.config = config;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
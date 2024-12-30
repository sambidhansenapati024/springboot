package com.alfaris.ipsh.authservice.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * The primary key class for the PSH_CAS_HRY database table.
 * 
 */
@Embeddable
public class PshCasHryPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_TIME")
	private java.util.Date dateTime;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "SCREEN_ID")
	private String screenId;

	@Column(name = "FUNCTION_ID")
	private String functionId;

	public PshCasHryPK() {
	}

	public java.util.Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(java.util.Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getScreenId() {
		return this.screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public String getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PshCasHryPK)) {
			return false;
		}
		PshCasHryPK castOther = (PshCasHryPK) other;
		return this.dateTime.equals(castOther.dateTime) && this.userId.equals(castOther.userId)
				&& this.screenId.equals(castOther.screenId) && this.functionId.equals(castOther.functionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dateTime.hashCode();
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.screenId.hashCode();
		hash = hash * prime + this.functionId.hashCode();

		return hash;
	}
}
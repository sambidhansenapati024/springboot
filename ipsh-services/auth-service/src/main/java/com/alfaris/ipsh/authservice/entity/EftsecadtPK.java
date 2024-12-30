package com.alfaris.ipsh.authservice.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/**
 * The primary key class for the EFTSECADT database table.
 * 
 */
@Embeddable
public class EftsecadtPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="USER_ID")
	private String userId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOG_TIME")
	private java.util.Date logTime;

	public EftsecadtPK() {
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public java.util.Date getLogTime() {
		return this.logTime;
	}
	public void setLogTime(java.util.Date logTime) {
		this.logTime = logTime;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EftsecadtPK)) {
			return false;
		}
		EftsecadtPK castOther = (EftsecadtPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.logTime.equals(castOther.logTime);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.logTime.hashCode();
		
		return hash;
	}
}
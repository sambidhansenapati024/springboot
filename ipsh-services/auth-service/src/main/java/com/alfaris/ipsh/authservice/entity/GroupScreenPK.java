package com.alfaris.ipsh.authservice.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * The primary key class for the EFTSECGSC database table.
 * 
 */
@Embeddable
public class GroupScreenPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="GROUP_ID")
	private String groupId;

	@Column(name="SCREEN_ID")
	private String screenId;

	public GroupScreenPK() {
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
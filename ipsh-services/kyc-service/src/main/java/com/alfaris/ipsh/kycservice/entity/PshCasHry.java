package com.alfaris.ipsh.kycservice.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the PSH_CAS_HRY database table.
 * 
 */
@Entity
@Table(name="PSH_CAS_HRY", schema = "IPSH")
public class PshCasHry implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PshCasHryPK id;

	@Column(name="ACTION_DESCRIPTION")
	private String actionDescription;

	@Column(name="ACTION_STATUS")
	private String actionStatus;

	@Lob
	@Column(name="CUR_DATA")
	private String curData;

	@Lob
	@Column(name="PREV_DATA")
	private String prevData;

	public PshCasHry() {
	}

	public PshCasHryPK getId() {
		return this.id;
	}

	public void setId(PshCasHryPK id) {
		this.id = id;
	}

	public String getActionDescription() {
		return this.actionDescription;
	}

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	public String getActionStatus() {
		return this.actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	public String getCurData() {
		return this.curData;
	}

	public void setCurData(String curData) {
		this.curData = curData;
	}

	public String getPrevData() {
		return this.prevData;
	}

	public void setPrevData(String prevData) {
		this.prevData = prevData;
	}

}
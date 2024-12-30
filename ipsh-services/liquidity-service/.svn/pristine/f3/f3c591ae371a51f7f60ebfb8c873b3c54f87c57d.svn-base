package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "PSH_USR_NTY",schema = "IPSH")
@NamedQuery(name = "PshActHry.findAll", query = "SELECT d FROM PshActHry d")
public class PshActHry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TASK_ID")
	private String taskId;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "USER_DEP")
	private String userDep;

	@Column(name = "MAPPED_VALUES")
	private String mappedValues;

	@Column(name = "SCREEN_ID")
	private String screenId;

	@Column(name = "TASK_MESSAGE")
	private String taskMessage;

	public PshActHry() {
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String creaedBy) {
		this.createdBy = creaedBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUserDep() {
		return this.userDep;
	}

	public void setUserDep(String customerId) {
		this.userDep = customerId;
	}

	public String getMappedValues() {
		return this.mappedValues;
	}

	public void setMappedValues(String mappedValues) {
		this.mappedValues = mappedValues;
	}

	public String getScreenId() {
		return this.screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public String getTaskMessage() {
		return this.taskMessage;
	}

	public void setTaskMessage(String taskMessage) {
		this.taskMessage = taskMessage;
	}

}